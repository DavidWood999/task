package com.wsy.dao.mapper;

import org.springframework.stereotype.Repository;

import com.wsy.domain.po.UserEntity;

@Repository
public interface UserDaoMapper {
	
	/*����id��ѯ�û�*/
	public UserEntity getUser(String uuid);

}
