package io.github.rcarlosdasilva.weixin.model;

import java.io.Serializable;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.cache.Cacheable;

public abstract class AccessToken implements Serializable, Cacheable {

  private static final long serialVersionUID = 3553323869805808582L;

  private String accountMark;
  private String accessToken;
  private String refreshToken;
  private int expiresIn;
  private long expireAt;

  public String getAccountMark() {
    return accountMark;
  }

  public void setAccountMark(String accountMark) {
    this.accountMark = accountMark;
  }

  /**
   * 获取到的凭证.
   * <p>
   * 对应<b>公众号接口</b>的access_token<br>
   * 对应<b>开放平台接口</b>的component_access_token<br>
   * 对应<b>开放平台授权方接口</b>的authorizer_access_token(授权方接口调用凭据（在授权的公众号或小程序具备API权限时，才有此返回值），也简称为令牌)
   * 
   * @return access_token
   */
  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  /**
   * （当使用开放平台时）授权方票据的刷新令牌(对应的是authorizer_refresh_token).
   * <p>
   * 接口调用凭据刷新令牌（在授权的公众号具备API权限时，才有此返回值），刷新令牌主要用于第三方平台获取和刷新已授权用户的access_token，只会在授权时刻提供，请妥善保存。
   * 一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌
   * 
   * @return refresh_token
   */
  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  /**
   * 凭证有效时间,单位:秒.
   * 
   * @return expires
   */
  public long getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
    updateExpireAt();
  }

  /**
   * 更新准确的过期时间，默认提前180秒过期.
   */
  protected void updateExpireAt() {
    this.expireAt = (this.expiresIn - Convention.AHEAD_OF_EXPIRED_SECONDS) * 1000
        + System.currentTimeMillis();
  }

  /**
   * 是否过期或无用.
   * 
   * @return is expired
   */
  public boolean isExpired() {
    return this.expireAt < System.currentTimeMillis() || Strings.isNullOrEmpty(this.accessToken);
  }

}
