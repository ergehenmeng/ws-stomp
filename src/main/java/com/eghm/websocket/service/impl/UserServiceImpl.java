package com.eghm.websocket.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.eghm.websocket.mapper.UserDao;
import com.eghm.websocket.model.User;
import com.eghm.websocket.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;
	
	/**
	 * 通过name phone email获取用户
	 * @return List
	 */
	@Cacheable(value="r")
    @Override
	public  User getUserByMap(Map<String,Object> map){
		return userDao.getUserByMap(map);
	}

	@Override
	public List<User> getUserFriendList(Integer userId, String workspaceId) {
		
		return userDao.getUserFriendList(userId,workspaceId);
	}
	
}
