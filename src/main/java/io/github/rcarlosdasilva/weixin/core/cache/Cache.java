package io.github.rcarlosdasilva.weixin.core.cache;

import java.util.Collection;
import java.util.Set;

/**
 * 缓存
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface Cache<V> {

  /**
   * 所有键.
   * 
   * @return 键
   */
  Set<String> keys();

  /**
   * 所有值
   * 
   * @return 值集合
   */
  Collection<V> values();

  /**
   * 缓存大小
   * 
   * @return int
   */
  int size();

  /**
   * 清空
   */
  void clear();

  /**
   * 获取.
   * 
   * @param key
   *          键
   * @return 值
   */
  V get(String key);

  /**
   * 放入.
   * 
   * @param key
   *          键
   * @param object
   *          值
   * @return 值
   */
  V put(String key, V object);

  /**
   * 移除.
   * 
   * @param key
   *          键
   * @return 值
   */
  V remove(String key);

  /**
   * 查找.
   * 
   * @param value
   *          值
   * @return boolean
   */
  String lookup(V value);

}
