package com.wsy.domain.filter;

import java.io.Serializable;

public class UserFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主键 */
	private String uuid;

	/* 真实姓名 */
	private String realname;

	/* 登录名 */
	private String loginname;

	/* 密码 */
	private String password;

	/* 性别 */
	private String sex;

	/* 年龄 */
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
