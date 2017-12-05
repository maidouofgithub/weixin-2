package io.github.rcarlosdasilva.weixin.test;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.model.response.user.BlackListQueryResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.UserOpenIdListResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.bean.User;
import io.github.rcarlosdasilva.weixin.test.basic.RegisterAndUse;

public class UserApiTest {

  @BeforeClass
  public static void setUpBeforeClass() {
    RegisterAndUse.reg();
  }

  @Test
  public void testListAllUsersOpenId() {
    UserOpenIdListResponse users = Weixin.withUnique().user().listAllUsersOpenId();
    Assert.assertNotNull(users);

    users = Weixin.withUnique().user().listAllUsersOpenId(users.getLastOpenId());
    Assert.assertNotNull(users);
  }

  @Test
  public void testUserInfoAndRemark() {
    UserOpenIdListResponse resp = Weixin.withUnique().user().listAllUsersOpenId();
    User user = Weixin.withUnique().user().getUserInfo(resp.getLastOpenId());
    Assert.assertNotNull(user);

    boolean success = Weixin.withUnique().user().remarkName(resp.getLastOpenId(), "newname");
    Assert.assertTrue(success);

    success = Weixin.withUnique().user().remarkName(resp.getLastOpenId(), user.getRemark());
    Assert.assertTrue(success);
  }

  @Test
  public void testGetUsersInfo() {
    UserOpenIdListResponse resp = Weixin.withUnique().user().listAllUsersOpenId();
    List<User> users = Weixin.withUnique().user()
        .getUsersInfo(Lists.newArrayList(resp.getLastOpenId()));
    Assert.assertNotNull(users);
    Assert.assertTrue(users.size() > 0);
  }

  @Test
  public void testListUsersOpenIdWithTagInt() {
    int tagid = Weixin.withUnique().userTag().create("temptag");

    UserOpenIdListResponse resp = Weixin.withUnique().user().listUsersOpenIdWithTag(tagid);
    Assert.assertNotNull(resp);

    Weixin.withUnique().userTag().delete(tagid);
  }

  @Test
  public void testBlackList() {
    UserOpenIdListResponse users = Weixin.withUnique().user().listAllUsersOpenId();

    boolean success = Weixin.withUnique().user()
        .appendUsersToBlack(Lists.newArrayList(users.getLastOpenId()));
    Assert.assertTrue(success);

    BlackListQueryResponse blacks = Weixin.withUnique().user().listUsersInBlack();
    Assert.assertNotNull(blacks);
    Assert.assertTrue(blacks.getCount() > 0);

    success = Weixin.withUnique().user()
        .cancelUsersFromBlack(Lists.newArrayList(users.getLastOpenId()));
    Assert.assertTrue(success);
  }

}
