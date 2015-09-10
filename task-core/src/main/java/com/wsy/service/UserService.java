package com.wsy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsy.dao.UserDao;
import com.wsy.domain.po.UserEntity;

@Service("userService")
public class UserService {

	@Autowired
	private UserDao userDao;

	public UserEntity findUserById(String uuid) {
		return userDao.findOneById(uuid);
	}
	
	public UserEntity getUserById(String uuid) {
		return userDao.getUser(uuid);
	}

}
