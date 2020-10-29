package com.eghm.websocket.service;

import java.util.List;
import java.util.Map;

import com.eghm.websocket.model.User;

public interface UserService {

    /**
     * 通过name phone email获取用户
     *
     * @return List
     */
    User getUserByMap(Map<String, Object> map);

    /**
     * 获取该工作空间中所有的用户
     *
     * @param userId
     * @param workspaceId
     * @return
     */
    List<User> getUserFriendList(Integer userId, String workspaceId);
}
