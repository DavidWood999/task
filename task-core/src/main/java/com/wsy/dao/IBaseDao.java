package com.wsy.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wsy.util.Page;

public interface IBaseDao<T> {
	/**
	 * ����(���Ὣ�������ɵ�ID,ע��) Ч�ʽ�save(T t)��
	 * 
	 * @param t
	 */
	void create(T t);

	/**
	 * ��������(���Ὣ�������ɵ�ID,ע��) Ч�ʽ�saveOfBatch(List<T> tList)��
	 * 
	 * @param tList
	 */
	void createOfBatch(Collection<T> tList);

	/**
	 * ����ID����ɾ��
	 * 
	 * @param id
	 */
	void removeById(Serializable id);

	/**
	 * ����ids��������ɾ��
	 * 
	 * @param ids
	 */
	void removeOfBatch(Collection<Serializable> ids);

	/**
	 * ����,�ֶ�Ϊ�գ��򲻽��и���
	 * 
	 * @param t
	 */
	void modify(T t);

	/**
	 * ��������
	 * 
	 * @param tList
	 */
	void modifyOfBatch(Collection<T> c);

	/**
	 * ����ID��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	T findOneById(Serializable id);
	
	/**
	 * ���� paramMap ��ѯ�б�
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	List<T> findList(String sql, Map<String, Object> paramMap);
	
	/**
	 * ���� paramMap ��ѯ�б�
	 * 
	 * @param sql
	 * @param paramMap
	 * @param model
	 * @return
	 */
	<M extends Serializable> List<M> findList(String sql, Map<String, Object> paramMap, Class<M> model);
	
	/**
	 * ��ѯ��ҳ���
	 * 
	 * @param sql
	 * @param paramMap
	 * @param page
	 * @param model
	 * @return
	 */
	<M extends Serializable> Page<M> getPage(String sql, Map<String, Object> paramMap, Page<M> page, Class<M> model);
}
