package io.github.rcarlosdasilva.weixin.model.request.comment;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

@SuppressWarnings("unused")
public class CommentUnstarRequest extends BasicWeixinRequest {

  @SerializedName("msg_data_id")
  private String messageDataId;
  private int index;
  @SerializedName("user_comment_id")
  private String commentId;

  public CommentUnstarRequest() {
    this.path = ApiAddress.URL_COMMENT_UNSTAR;
  }

  public void setMessageDataId(String messageDataId) {
    this.messageDataId = messageDataId;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

}
