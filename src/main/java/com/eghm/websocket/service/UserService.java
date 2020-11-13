package com.eghm.websocket.service;

import java.util.List;
import java.util.Map;

import com.eghm.websocket.model.User;

/**
 * @author 二哥很猛
 */
public interface UserService {

    /**
     * 用户登陆
     * @param mobile 手机号码
     * @param pwd pwd(md5格式)
     * @return user
     */
    User login(String mobile, String pwd);

    /**
     * 获取该工作空间中所有的用户
     * @param userId  用户 (用于排序)
     * @param spaceId spaceId
     * @return
     */
    List<User> getFriendList(Long userId, Long spaceId);
}
