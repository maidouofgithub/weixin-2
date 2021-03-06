package io.github.rcarlosdasilva.weixin.model.request.menu;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取自定义菜单配置请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class MenuQueryCompleteRequest extends BasicWeixinRequest {

  public MenuQueryCompleteRequest() {
    this.path = ApiAddress.URL_MENU_QUERY_COMPLETE;
  }

}
