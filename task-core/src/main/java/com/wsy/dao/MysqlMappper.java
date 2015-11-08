package com.wsy.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class MysqlMappper<T> extends BaseDaoMapperAbstract<T> {

	@Override
	protected <M extends Serializable> List<M> getPage(String sql, Map<String, Object> paramMap, int startRow, int pageSize, Class<M> model) {
		sql += " Limit #{startRrow}, #{pageSize}";
		paramMap.put("startRrow", startRow);
		paramMap.put("pageSize", pageSize);
		
		List<Map<String, Object>> resultMapList = sqlSessionTemplateASS.selectList("findList", paramMap);
		List<M> tList = new ArrayList<M>(resultMapList.size());
		for (Map<String, Object> resultMap : resultMapList) {
			M t = handleResultM(resultMap, model);
			tList.add(t);
		}
		
		printSQL.info(sql);
		printSQL.info(paramMap.toString());
		
		return (List<M>) tList;
	}

}
