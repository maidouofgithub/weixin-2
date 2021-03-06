package io.github.rcarlosdasilva.weixin.model.request.custom;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 删除客服账号请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class CustomAccountDeleteRequest extends BasicWeixinRequest {

  private String account;

  public CustomAccountDeleteRequest() {
    this.path = ApiAddress.URL_CUSTOM_ACCOUNT_DELETE;
  }

  public void setAccount(String account) {
    this.account = account;
    this.path = ApiAddress.URL_CUSTOM_ACCOUNT_DELETE;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?access_token=").append(this.accessToken)
        .append("&kf_account=").append(this.account).toString();
  }

}
