package com.wsy.dao.mapper;

import org.springframework.stereotype.Repository;

import com.wsy.domain.po.UserEntity;

@Repository
public interface UserDaoMapper {
	
	/*根据id查询用户*/
	public UserEntity getUser(String uuid);

}
