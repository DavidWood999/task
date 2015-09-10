package com.wsy.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wsy.util.AssConstant;
import com.wsy.util.DataUtil;
import com.wsy.util.GenericsUtils;
import com.wsy.util.ModelMapper;
import com.wsy.util.Page;
import com.wsy.util.ReflectionUtils;

@Repository
public abstract class BaseDaoMapperAbstract<T> implements IBaseDao<T> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected Logger printSQL = LoggerFactory.getLogger("生成的SQL为: ");

	@Autowired
	public SqlSessionTemplate sqlSessionTemplateASS;

	@SuppressWarnings("unchecked")
	protected Class<T> entityClass = (Class<T>) GenericsUtils.getSuperClassGenricType(this.getClass());
	
	private static final Map<Class<?>, ModelMapper> modelMap = new HashMap<Class<?>, ModelMapper>();
	
	private ModelMapper getGenericMapper(){
		if( !modelMap.containsKey(entityClass) ){
			ModelMapper mapper = new ModelMapper();
			mapper.load(entityClass);
			modelMap.put(entityClass, mapper);
		}
		
		return modelMap.get(entityClass);
	}
	
	public void create(T t){
		create(t, true);
	}

	public void create(T t, boolean saveNullColumn) {
		ModelMapper mapper = getGenericMapper();
		
		ArrayList<String> fieldList = new ArrayList<String>(mapper.getFieldMapper().size());
		ArrayList<Object> valueList = new ArrayList<Object>(mapper.getFieldMapper().size());
		
		for (Field field : mapper.getFieldMapper().keySet()) {
			try {
				Object value = ReflectionUtils.obtainFieldValue(t, mapper.getFieldMapper().get(field) );
				if( saveNullColumn || value != null ){
					fieldList.add( mapper.getFieldMapper().get(field).toUpperCase() );
					valueList.add( handleValue(value) );
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String columnsStr = StringUtils.join(fieldList, ",");
		String values = StringUtils.join(valueList, ",");
		
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("INSERT INTO ").append( mapper.getTableName() ).append("(")
                .append(columnsStr).append(")values(")
                .append(values).append(")");
        String sql = sql_build.toString();
         
        printSQL.debug(sql);
		
		sqlSessionTemplateASS.insert("create", sql);
	}
	
	@SuppressWarnings("all")
	public void createOfBatch(Collection<T> tList){
		ModelMapper mapper = getGenericMapper();
		ArrayList<String> fieldList = new ArrayList<String>(mapper.getFieldMapper().size());
		Map[] maps = new Map[tList.size()];
		for(int i=0, n = tList.size(); i < n; i++){
			maps[i] = new LinkedHashMap();
		}
		
		for (Field field : mapper.getFieldMapper().keySet()) {
			try {
				fieldList.add( mapper.getFieldMapper().get(field).toUpperCase() );
				
				int i = 0;
				for (T t : tList) {
					String fieldName = mapper.getFieldMapper().get(field);
					Object value = ReflectionUtils.obtainFieldValue(t, fieldName );
					maps[i++].put(fieldName,  handleValue(value) );
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String columnsStr = StringUtils.join(fieldList, ",");
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("INSERT INTO ").append( mapper.getTableName() ).append("(").append(columnsStr).append(") values");

		int i = 0;
		int n = maps.length;
		for (Map map : maps) {
			i++;
			String value = map.values().toString();
			value = value.substring(1, value.length() -1 );
			sql_build.append("(" + value + ")");
			
			//除了最后一行，其他都在后面加 ','
			if(i != n){
				sql_build.append(",");
			}
		}
		String sql = sql_build.toString();
		printSQL.debug(sql);
		
		sqlSessionTemplateASS.insert("createOfBatch", sql);
	}
	
	public void removeById(Serializable id) {
		ModelMapper mapper = getGenericMapper();
		
		StringBuilder sql_build = new StringBuilder();
	    sql_build.append("DELETE FROM ").append(mapper.getTableName()).append(" WHERE ").
	    	append(mapper.getPrimaryKey()).append(" = ").append(handleValue(id));
	         
	    String sql = sql_build.toString();
	    printSQL.debug(sql);
		
		sqlSessionTemplateASS.delete("removeById", sql);
	}
	
	public void removeOfBatch(Collection<Serializable> ids) {
		if (null == ids || ids.isEmpty()) {
			return;
		}
		
		ModelMapper mapper = getGenericMapper();
		
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("DELETE FROM ").append(mapper.getTableName())
                .append(" WHERE ").append(mapper.getPrimaryKey()).append(" IN ( 0 ");
		for (Object id : ids) {
			sql_build.append("," + handleValue(id));
		}
		sql_build.append(")");
		
	    String sql = sql_build.toString();
	    printSQL.debug(sql);
		
		sqlSessionTemplateASS.delete("removeOfBatch", sql);
	}
	
	public void modify(T t) {
		modify(t, true);
	}
	
	public void modify(T t, boolean saveNullColumn) {
		ModelMapper mapper = getGenericMapper();
		
		ArrayList<String> colsValueList = new ArrayList<String>(mapper.getFieldMapper().size());
		
		Field primaryField = mapper.getPrimaryKeyField();
		for (Field field : mapper.getFieldMapper().keySet()) {
			if(field == primaryField){ //主键不更新
				continue;
			}
			
			try {
				Object value = ReflectionUtils.obtainFieldValue(t, mapper.getFieldMapper().get(field) );
				if( saveNullColumn || value != null ){
					colsValueList.add( mapper.getFieldMapper().get(field).toUpperCase() + " = " + handleValue(value));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String colsValues = StringUtils.join(colsValueList, ",");
		
		Object id = ReflectionUtils.obtainFieldValue(t, mapper.getPrimaryKeyField().getName());
        
		StringBuilder sql_build = new StringBuilder();
        sql_build.append("UPDATE ").append( mapper.getTableName() ).append(" SET ")
                .append(colsValues).append(" WHERE ")
                .append(mapper.getPrimaryKey()).append(" = ").append( handleValue(id) );
         
        String sql = sql_build.toString();
        
        printSQL.debug(sql);
        System.out.println(sql);
		
		sqlSessionTemplateASS.insert("modify", sql);
	}

	@SuppressWarnings("all")
	public void modifyOfBatch(Collection<T> c) {
		if (null == c || c.isEmpty()) {
			return;
		}
		
		int i = -1;
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			T t = (T) iterator.next();
			this.modify( t );
			
			i++;
			
			if (i > 0 && i % AssConstant.FLUSH_CRITICAL_VAL == 0) {
				sqlSessionTemplateASS.flushStatements();
			}
		}
	}
	
	public T findOneById(Serializable id) {
		ModelMapper mapper = getGenericMapper();
		
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("SELECT ").append( getColumnNames("") ).append(" FROM ")
        		.append(mapper.getTableName())
                .append(" WHERE " + mapper.getPrimaryKey() + " = #{UUID}");
        
        String sql = sql_build.toString();
        printSQL.debug(sql);
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("UUID", id);
        
        List<T> list = findList(sql, paramMap);
        
        if(list != null && list.size() > 1){
        	try {
				throw new  Exception("主键不唯一。。。");
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
        return list == null || list.size() == 0 ? null : list.get(0);
	}
	
/*	public T findOneById(Serializable id) {
		ModelMapper mapper = getGenericMapper();
		
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("SELECT ").append( getColumnNames("") ).append(" FROM ")
                .append(mapper.getTableName())
                .append(" WHERE " + mapper.getPrimaryKey() + " = " + handleValue(id) );
        
        String sql = sql_build.toString();
        printSQL.debug(sql);
        
		Map<String, Object> resultMap = sqlSessionTemplateASS.selectOne("findOneById", sql);

		return resultMap == null? null : handleResult(resultMap, this.entityClass);
	}*/
	
	public List<T> findList(String sql, Map<String, Object> paramMap) {
		paramMap.put("value", sql);
		printSQL.debug(sql);
		
		List<Map<String, Object>> resultMapList = sqlSessionTemplateASS.selectList("findList", paramMap);
		List<T> tList = new ArrayList<T>(resultMapList.size());
		for (Map<String, Object> resultMap : resultMapList) {
			T t = handleResult(resultMap, this.entityClass);
			tList.add(t);
		}
		
		return tList;
	}
	
	public <M extends Serializable> List<M> findList(String sql, Map<String, Object> paramMap, Class<M> model) {
		paramMap.put("value", sql);
		
		List<Map<String, Object>> resultMapList = sqlSessionTemplateASS.selectList("findList", paramMap);
		List<M> tList = new ArrayList<M>(resultMapList.size());
		for (Map<String, Object> resultMap : resultMapList) {
			M t = handleResultM(resultMap, model);
			tList.add(t);
		}
      
		printSQL.debug(sql);
      
		return tList;
	}
	
	/**
	 * 查询出分页结果记录
	 * 
	 * @param sql
	 * @param paramMap
	 * @param startRow
	 * @param pageSize
	 * @param model
	 * @return
	 */
	protected abstract <M extends Serializable> List<M> getPage(String sql, Map<String, Object> paramMap, int startRow, int pageSize, Class<M> model);
	
	/**
	 * 算出总记录数
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	private Integer getTotalCount(String sql, Map<String, Object> paramMap){
		sql = sql.toUpperCase();
		String sqlPost = sql.substring(sql.indexOf("FROM"));
		StringBuilder sqlCount = new StringBuilder(" SELECT COUNT(1) ");
		sqlCount.append(sqlPost);
		sql = sqlCount.toString();
		paramMap.put("value", sql);
		
		Integer count = sqlSessionTemplateASS.selectOne("findNumber", paramMap);
		return count;
	}
	
	public <M extends Serializable> Page<M> getPage(String sql, Map<String, Object> paramMap, Page<M> page, Class<M> model){
		page.setTotalRow(getTotalCount(sql, paramMap));
		paramMap.put("value", sql);
		page.setResultRows(getPage(sql, paramMap, page.getStartRow(), page.getPageSize(), model));
		return page;
	}
	

	
	//============================================
	/**
	 * T 获取数据库字段名，以逗号隔开
	 * 
	 * @param mapper
	 * @param aliasTableName
	 * @return
	 */
	public String getColumnNames(String aliasTableName){
		ModelMapper mapper = getGenericMapper();
		ArrayList<String> fieldList = new ArrayList<String>(mapper.getFieldMapper().size());
		
		for (Field field : mapper.getFieldMapper().keySet()) {
			try {
				if(DataUtil.isNullOrEmpty(aliasTableName)){
					fieldList.add( mapper.getFieldMapper().get(field).toUpperCase() );
				}else {
					fieldList.add( aliasTableName + "." +mapper.getFieldMapper().get(field).toUpperCase() );
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String columnsStr = StringUtils.join(fieldList, ",");
		
		return columnsStr;
	}

	/**
	 * 把查询的结果与实体 T 映射
	 * 
	 * @param resultMap
	 * @param tClazz
	 * @return
	 */
	private T handleResult(Map<String, Object> resultMap, Class<T> tClazz) {
		ModelMapper mapper = getGenericMapper();
		
		T t = null;
		try {
			t = tClazz.newInstance();
		} catch (InstantiationException e) {
			logger.error("/********************************");
			logger.error("封装查询结果时，实例化对象(" + this.entityClass + ")时，出现异常!"
					+ e.getMessage());
			logger.error("/********************************");
		} catch (IllegalAccessException e) {
			logger.error("/********************************");
			logger.error("封装查询结果时，实例化对象(" + this.entityClass + ")时，出现异常!"
					+ e.getMessage());
			logger.error("/********************************");
		}
		for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
			String key = entry.getKey();
			key = mapper.getFieldNameMapper().get(key);
			Object val = entry.getValue();
			
			if(val instanceof java.sql.Date){
				val = new Date(((java.sql.Date) val).getTime());
			}
			
			ReflectionUtils.invokeSetterMethod(t, key, val);
		}
		return t;
	}
	
	/**
	 * 把查询的结果与实体 M 映射
	 * 
	 * @param resultMap
	 * @param model
	 * @return
	 */
	protected <M extends Serializable> M handleResultM(Map<String, Object> resultMap, Class<M> model) {
		ModelMapper mapper = new ModelMapper();
		mapper.genericClass(model);
		
		M m = null;
		try {
			m = model.newInstance();
		} catch (InstantiationException e) {
			logger.error("/********************************");
			logger.error("封装查询结果时，实例化对象(" + model + ")时，出现异常!" + e.getMessage());
			logger.error("/********************************");
		} catch (IllegalAccessException e) {
			logger.error("/********************************");
			logger.error("封装查询结果时，实例化对象(" + model + ")时，出现异常!" + e.getMessage());
			logger.error("/********************************");
		}
		for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
			String key = entry.getKey();
			key = mapper.getFieldNameMapper().get(key);
			Object val = entry.getValue();
			
			if(val instanceof java.sql.Date){
				val = new Date(((java.sql.Date) val).getTime());
			}
			
			ReflectionUtils.invokeSetterMethod(m, key, val);
		}
		return m;
	}
     
    /**
     * 处理value
     * 
     * @param value
     * @return
     */
    private Object handleValue(Object value) {
        if (value instanceof String) {
            value = "\'" + value + "\'";
        } else if (value instanceof Date) {
            Date date = (Date) value;
            String dateStr = DataUtil.date2str(date);
            value = "'" + dateStr + "'";
        } else if (value instanceof Boolean) {
            Boolean v = (Boolean) value;
            value = v ? 1 : 0;
        }else if(null == value || StringUtils.isBlank(value.toString())){
        	value = null;
        }
        return value;
    }

}