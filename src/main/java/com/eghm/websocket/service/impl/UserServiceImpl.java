package com.eghm.websocket.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.eghm.websocket.mapper.UserMapper;
import com.eghm.websocket.model.User;
import com.eghm.websocket.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserMapper userMapper;
	
	/**
	 * 通过name phone email获取用户
	 * @return List
	 */
	@Cacheable(value="r")
    @Override
	public  User getUserByMap(Map<String,Object> map){
		return userMapper.getUserByMap(map);
	}

	@Override
	public List<User> getUserFriendList(Integer userId, String workspaceId) {
		
		return userMapper.getUserFriendList(userId,workspaceId);
	}
	
}
