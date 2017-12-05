package io.github.rcarlosdasilva.weixin.core.cache.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.Utils;
import io.github.rcarlosdasilva.weixin.core.Registry;
import io.github.rcarlosdasilva.weixin.core.cache.CacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.holder.MapHandler;
import io.github.rcarlosdasilva.weixin.core.cache.holder.RedisTemplateHandler;
import io.github.rcarlosdasilva.weixin.core.cache.holder.SimpleRedisHandler;
import redis.clients.jedis.Jedis;

public class AbstractCacheHandler<V> implements CacheHandler<V> {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private static final String REDIS_KEY_PREFIX = Convention.DEFAULT_REDIS_KEY_PREFIX
      + Convention.DEFAULT_REDIS_KEY_SEPARATOR;
  private static final Splitter SPLITTER = Splitter.on(Convention.DEFAULT_REDIS_KEY_SEPARATOR);

  protected String mark;

  protected boolean isRedis() {
    return Registry.handler().getSetting().isUseRedisCache();
  }

  protected boolean isSimpleRedis() {
    return Registry.handler().getSetting().isUseRedisCache()
        && !Registry.handler().getSetting().isUseSpringRedis();
  }

  protected boolean isSpringRedis() {
    return Registry.handler().getSetting().isUseSpringRedis();
  }

  private static String key(final String module, final String resource) {
    return new StringBuilder(REDIS_KEY_PREFIX).append(module)
        .append(Convention.DEFAULT_REDIS_KEY_SEPARATOR).append(resource).toString();
  }

  private String realRedisKey(final String key) {
    return key(mark, key);
  }

  private String realRedisKeyPattern() {
    return realRedisKey(Convention.DEFAULT_REDIS_KEY_PATTERN);
  }

  private Collection<String> realKeys(final Set<String> redisKeys) {
    if (redisKeys == null) {
      return Collections.emptyList();
    }

    return Collections2.transform(redisKeys, new Function<String, String>() {

      @Override
      public String apply(String input) {
        if (!Strings.isNullOrEmpty(input)) {
          List<String> parts = SPLITTER.splitToList(input);
          if (parts != null && !parts.isEmpty()) {
            return parts.get(parts.size() - 1);
          }
        }
        return "";
      }

    });
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<String> keys() {
    if (isSimpleRedis()) {
      Jedis jedis = SimpleRedisHandler.getRedis();
      Collection<String> realKeys = realKeys(jedis.keys(String.valueOf(realRedisKeyPattern())));
      SimpleRedisHandler.returnRedis(jedis);
      return realKeys;
    } else if (isSpringRedis()) {
      return realKeys(RedisTemplateHandler.redisTemplate.keys(realRedisKeyPattern()));
    } else {
      return MapHandler.getObject(mark).keySet();
    }
  }

  @Override
  public int size() {
    if (isSimpleRedis() || isSpringRedis()) {
      return keys().size();
    } else {
      return MapHandler.<V>getObject(mark).size();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void clear() {
    if (isSimpleRedis()) {
      Collection<String> keys = keys();
      for (String key : keys) {
        remove(key);
      }
    } else if (isSpringRedis()) {
      RedisTemplateHandler.redisTemplate.delete(keys());
    } else {
      MapHandler.<V>getObject(mark).clear();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public V get(final String key) {
    if (Strings.isNullOrEmpty(key)) {
      return null;
    }

    if (isSimpleRedis()) {
      Jedis jedis = SimpleRedisHandler.getRedis();
      byte[] value = jedis.get(realRedisKey(key).getBytes());
      jedis.close();
      if (value == null) {
        return null;
      }
      return Utils.unserialize(value);
    } else if (isSpringRedis()) {
      return (V) RedisTemplateHandler.redisTemplate.opsForValue().get(realRedisKey(key));
    } else {
      try {
        Map<String, V> cache = MapHandler.<V>getObject(mark);
        if (cache.containsKey(key)) {
          return cache.get(key);
        }
        return null;
      } catch (Exception ex) {
        logger.error("weixin abstract cache get", ex);
        return null;
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public V put(final String key, final V object) {
    if (isSimpleRedis()) {
      Jedis jedis = SimpleRedisHandler.getRedis();
      jedis.set(realRedisKey(key).getBytes(), Utils.serialize(object));
      jedis.close();
    } else if (isSpringRedis()) {
      RedisTemplateHandler.redisTemplate.opsForValue().set(realRedisKey(key), object);
    } else {
      try {
        MapHandler.<V>getObject(mark).put(key, object);
      } catch (Exception ex) {
        logger.error("weixin abstract cache put", ex);
        return null;
      }
    }
    return object;
  }

  @SuppressWarnings("unchecked")
  @Override
  public V remove(final String key) {
    if (isSimpleRedis()) {
      V object = get(key);
      if (object == null) {
        return null;
      }

      Jedis jedis = SimpleRedisHandler.getRedis();
      jedis.del(realRedisKey(key));
      jedis.close();
      return object;
    } else if (isSpringRedis()) {
      V object = get(key);
      if (object == null) {
        return null;
      }

      RedisTemplateHandler.redisTemplate.delete(realRedisKey(key));
      return object;
    } else {
      try {
        return MapHandler.<V>getObject(mark).remove(key);
      } catch (Exception ex) {
        logger.error("weixin abstract cache", ex);
        return null;
      }
    }
  }

  @Override
  public V lookup(final V value) {
    return null;
  }

}
