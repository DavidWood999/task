package com.wsy.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDO<T> implements Comparable<T>, Serializable {

	private static final long serialVersionUID = -5163262369461780617L;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
