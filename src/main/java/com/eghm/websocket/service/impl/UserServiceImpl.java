package com.eghm.websocket.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.digest.BCrypt;
import com.eghm.websocket.enums.ErrorCode;
import com.eghm.websocket.exception.SystemException;
import com.eghm.websocket.mapper.UserMapper;
import com.eghm.websocket.model.User;
import com.eghm.websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * @author 二哥很猛
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String mobile, String pwd) {
        if (!Validator.isMobile(mobile)) {
            throw new SystemException(ErrorCode.MOBILE_ERROR);
        }
        User user = userMapper.getByMobile(mobile);
        if (user == null) {
            throw new SystemException(ErrorCode.USER_FOUND_ERROR);
        }
        if (user.getState() == User.LOCK) {
            throw new SystemException(ErrorCode.USER_LOCK_ERROR);
        }
        if (!BCrypt.checkpw(pwd, user.getPwd())) {
            throw new SystemException(ErrorCode.USER_PWD_ERROR);
        }
        return user;
    }

    @Override
    public List<User> getFriendList(Long userId, Long spaceId) {
        List<User> friendList = userMapper.getFriendList(spaceId);
        if (CollUtil.isNotEmpty(friendList)) {
            // 排序 自己排在最前面
            friendList.sort(Comparator.comparing(user -> !user.getId().equals(userId)));
        }
        return friendList;
    }

}
