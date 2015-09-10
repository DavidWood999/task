package com.wsy.util;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModelMapper {

	/* ӳ��� */
	private String tableName = null;

	/* ������ */
	private Field primaryKeyField = null;
	
	private String primaryKey = null;

	/* �ֶ�ӳ�� */
	private Map<Field, String> fieldMapper = new LinkedHashMap<Field, String>();
	
	// ���� ���ݿ���ֶ���entity���Զ�Ӧ�Ĺ�ϵ
	private Map<String, String> fieldNameMapper = new LinkedHashMap<String, String>();

	public void load(Class<?> entityClass) {
		Table table = entityClass.getAnnotation(Table.class);
		if (null == table) {
			throw new RuntimeException("��-" + entityClass + ",δ��@Tableע���ʶ!!");
		}else{
			tableName = table.value();
		}
		
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			// ���� ���ݿ���ֶ���entity���Զ�Ӧ�Ĺ�ϵ
			fieldNameMapper.put(field.getName().toUpperCase(), field.getName());
			
			if (field.getName().equals("serialVersionUID") || field.isAnnotationPresent(Ignore.class)) {
				continue;
				
			}else if(field.isAnnotationPresent(TableColumn.class)){
				TableColumn tableColumn = field.getAnnotation(TableColumn.class);
				if( !DataUtil.isNullOrEmpty(tableColumn.value()) ){
					this.fieldMapper.put(field, tableColumn.value());
				}else{
					this.fieldMapper.put(field, field.getName());
				}
				
			}else if (field.isAnnotationPresent(PrimaryKey.class)) {
				PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
				primaryKeyField = field;
				this.primaryKey = field.getName().toUpperCase();
				
				if( !DataUtil.isNullOrEmpty(primaryKey.value()) ){
					this.fieldMapper.put(field, primaryKey.value());
				}else{
					this.fieldMapper.put(field, field.getName());
				}
				
			}else {
				this.fieldMapper.put(field, field.getName());
			}
		} // end for
	}
	
	public void genericClass(Class<?> entityClass) {
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			// ���� ���ݿ���ֶ���entity���Զ�Ӧ�Ĺ�ϵ
			fieldNameMapper.put(field.getName().toUpperCase(), field.getName());
		} // end for
	}

	/* ӳ��� */
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/* ������ */
	public Field getPrimaryKeyField() {
		return primaryKeyField;
	}

	public void setPrimaryKeyField(Field primaryKeyField) {
		this.primaryKeyField = primaryKeyField;
	}

	/* �ֶ�ӳ�� */
	public Map<Field, String> getFieldMapper() {
		return fieldMapper;
	}

	public void setFieldMapper(Map<Field, String> fieldMapper) {
		this.fieldMapper = fieldMapper;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Map<String, String> getFieldNameMapper() {
		return fieldNameMapper;
	}

	public void setFieldNameMapper(Map<String, String> fieldNameMapper) {
		this.fieldNameMapper = fieldNameMapper;
	}

}
