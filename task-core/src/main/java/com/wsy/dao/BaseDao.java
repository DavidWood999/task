package com.wsy.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BaseDao<T> extends MysqlMappper<T> {

}
