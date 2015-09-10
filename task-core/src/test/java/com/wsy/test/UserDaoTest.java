package com.wsy.test;

/*import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wsy.dao.UserDao;
import com.wsy.domain.po.UserEntity;
import com.wsy.domain.qo.UserDto;
import com.wsy.util.DataUtil;
import com.wsy.util.Page;
*/
public class UserDaoTest {
/*
	private ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
	private UserDao userDao = (UserDao) ctx.getBean("userDao");

	@Test
	public void testInsert() {
		UserEntity user = new UserEntity();
		user.setUuid(DataUtil.nextUUID());
		//user.setRealname("张三");
		user.setLoginname("DavidWood");
		user.setPassword("654321***");
		user.setAge(25);
		user.setSex("女");

		userDao.create(user);
	}
	
	@Test
	public void testInsertBatch() {
		List<UserEntity> lists= new ArrayList<UserEntity>();
		for (int i = 0; i < 3; i++) {
			UserEntity user = new UserEntity();
			user.setUuid(DataUtil.nextUUID());
			//user.setRealname("张三" + i);
			user.setLoginname("wind" + i);
			user.setPassword("123456" + i);
			user.setAge(10 + i);
			user.setSex("男");
			
			lists.add(user);
		}
		
		userDao.createOfBatch(lists);
	}
	
	@Test
	public void testDelete() {
		userDao.removeById("0bd51de702684ae8ba423c3bde4f207e");
	}
	
	@Test
	public void testDeleteatch() {
		List<Serializable> ids = new ArrayList<Serializable>();
		ids.add("975c8b6bac08479a9f6a1817c8a8f4a5");
		ids.add("b06f5fba1ad04fe986cd93baf018c7fa");
		
		userDao.removeOfBatch(ids);
	}
	
	@Test
	public void testUpdate() {
		UserEntity user = userDao.findOneById("ebce196a6f404aa2b59e78c059fabed1");
		user.setRealname("张三");
		user.setAge(98);
		userDao.modify(user);
		
		System.out.println(user);
	}
	
	@Test
	public void testUpdateBatch() {
		UserEntity user1 = userDao.findOneById("08626489275d4a43b2ebbde07884f9e1");
		user1.setRealname("张三");
		user1.setAge(11);
		user1.setBirthdate(new Date());
		
		UserEntity user2 = userDao.findOneById("1a85bfb7d9204af79ccebed78a1aaa5c");
		user2.setRealname("李四");
		user2.setAge(22);
		user2.setBirthdate(new Date());
		
		List<UserEntity> list = new ArrayList<UserEntity>();
		list.add(user1);
		list.add(user2);
		
		userDao.modifyOfBatch(list);
		
		for (UserEntity u : list) {
			System.out.println(u);
		}
	}
	
	@Test
	public void testGetUserById() {
		UserEntity user = userDao.getUser("ebce196a6f404aa2b59e78c059fabed1");
		System.out.println(user);
	}
	
	@Test
	public void testFindOneById() {
		UserEntity user = userDao.findOneById("08626489275d4a43b2ebbde07884f9e1");
		System.out.println(user);
	}
	
	@Test
	public void testFindList() {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("SELECT ").append(userDao.getColumnNames("")).append(" FROM ").append(" t_user ");
        sql_build.append(" WHERE REALNAME = #{REALNAME} ");
//        sql_build.append(" AND AGE = #{AGE}");
        String sql = sql_build.toString();
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("REALNAME", "张三");
//        paramMap.put("AGE", 98);
        
		List<UserEntity> list = userDao.findList(sql, paramMap);
		
		for (UserEntity userEntity : list) {
			System.out.println(userEntity);
		}
	}
	
	@Test
	public void testFindListDto() {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("SELECT ").append(userDao.getColumnNames("")).append(" FROM ").append(" t_user ");
        sql_build.append(" WHERE REALNAME = #{REALNAME} ");
//        sql_build.append(" AND AGE = #{AGE}");
        String sql = sql_build.toString();
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("REALNAME", "张三");
//        paramMap.put("AGE", 98);
        
		List<UserDto> list = userDao.findList(sql, paramMap, UserDto.class);
		
		for (UserDto u : list) {
			System.out.println(u);
		}
	}
	
	@Test
	public void testPage() {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("SELECT ").append(userDao.getColumnNames("")).append(" FROM ").append(" t_user ");
        sql_build.append(" WHERE REALNAME = #{REALNAME} ");
        String sql = sql_build.toString();
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("REALNAME", "张三");
        
        Page<UserEntity> page = new Page<UserEntity>(10, 1);
        page = userDao.getPage(sql, paramMap, page, UserEntity.class);
		
		for (UserEntity u : page.getResultRows()) {
			System.out.println(u);
		}
		
		System.out.println(page);
	}
	*/
}
