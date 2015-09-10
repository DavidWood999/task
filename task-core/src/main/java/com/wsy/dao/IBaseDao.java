package com.wsy.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wsy.util.Page;

public interface IBaseDao<T> {
	/**
	 * 新增(不会将序列生成的ID,注入) 效率较save(T t)高
	 * 
	 * @param t
	 */
	void create(T t);

	/**
	 * 批量新增(不会将序列生成的ID,注入) 效率较saveOfBatch(List<T> tList)高
	 * 
	 * @param tList
	 */
	void createOfBatch(Collection<T> tList);

	/**
	 * 根据ID进行删除
	 * 
	 * @param id
	 */
	void removeById(Serializable id);

	/**
	 * 根据ids进行批量删除
	 * 
	 * @param ids
	 */
	void removeOfBatch(Collection<Serializable> ids);

	/**
	 * 更新,字段为空，则不进行更新
	 * 
	 * @param t
	 */
	void modify(T t);

	/**
	 * 批量更新
	 * 
	 * @param tList
	 */
	void modifyOfBatch(Collection<T> c);

	/**
	 * 根据ID获取对象
	 * 
	 * @param id
	 * @return
	 */
	T findOneById(Serializable id);
	
	/**
	 * 根据 paramMap 查询列表
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	List<T> findList(String sql, Map<String, Object> paramMap);
	
	/**
	 * 根据 paramMap 查询列表
	 * 
	 * @param sql
	 * @param paramMap
	 * @param model
	 * @return
	 */
	<M extends Serializable> List<M> findList(String sql, Map<String, Object> paramMap, Class<M> model);
	
	/**
	 * 查询分页结果
	 * 
	 * @param sql
	 * @param paramMap
	 * @param page
	 * @param model
	 * @return
	 */
	<M extends Serializable> Page<M> getPage(String sql, Map<String, Object> paramMap, Page<M> page, Class<M> model);
}
