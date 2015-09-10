package com.wsy.domain.filter;

import java.io.Serializable;

public class UserFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	/* ���� */
	private String uuid;

	/* ��ʵ���� */
	private String realname;

	/* ��¼�� */
	private String loginname;

	/* ���� */
	private String password;

	/* �Ա� */
	private String sex;

	/* ���� */
	private Integer age;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "UserEntity [uuid=" + uuid + ", realname=" + realname
				+ ", loginname=" + loginname + ", password=" + password
				+ ", sex=" + sex + ", age=" + age + "]";
	}

}
