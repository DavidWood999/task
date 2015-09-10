package com.wsy.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wsy.dao.mapper.UserDaoMapper;
import com.wsy.domain.po.UserEntity;

@Repository("userDao")
public class UserDao extends BaseDao<UserEntity> {
	
	@Autowired
	private UserDaoMapper userDaoMapper;
	
	/*根据id查询用户*/
	public UserEntity getUser(String uuid){
		return userDaoMapper.getUser(uuid);
	}

}
