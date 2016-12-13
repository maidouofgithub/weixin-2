package io.github.rcarlosdasilva.weixin.model.request.media;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.bean.Article;

/**
 * 新增图文素材请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MediaAddTimelessNewsRequest extends BasicRequest {

  private List<Article> articles;

  public MediaAddTimelessNewsRequest() {
    this.path = ApiAddress.URL_MEDIA_TIMELESS_ADD_NEWS;
  }

  public void setArticles(List<Article> articles) {
    this.articles = articles;
  }

  /**
   * 添加一个 {@link Article}.
   */
  public void addArticle(Article article) {
    if (this.articles == null) {
      this.articles = Lists.newArrayList();
    }
    this.articles.add(article);
  }

}
