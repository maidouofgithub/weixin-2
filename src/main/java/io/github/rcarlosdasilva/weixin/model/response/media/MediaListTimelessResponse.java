package io.github.rcarlosdasilva.weixin.model.response.media;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.media.bean.Media;

public class MediaListTimelessResponse {

  private int totalCount;
  private int itemCount;
  @SerializedName("item")
  private List<Media> items;

  /**
   * 该类型的素材的总数.
   */
  public int getTotalCount() {
    return totalCount;
  }

  /**
   * 本次调用获取的素材的数量.
   */
  public int getItemCount() {
    return itemCount;
  }

  /**
   * 素材内容列表.
   */
  public List<Media> getItems() {
    return items;
  }

}
