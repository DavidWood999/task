package com.wsy.domain.qo;

import java.io.Serializable;
import java.util.Date;


public class UserDto implements Serializable{
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
	
	/* 出生日期 */
	private Date birthdate;

	/* 年龄 */
	private Integer age;
	
	//
	private String addr;

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

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return "UserDto [uuid=" + uuid + ", realname=" + realname
				+ ", loginname=" + loginname + ", password=" + password
				+ ", sex=" + sex + ", birthdate=" + birthdate + ", age=" + age
				+ ", addr=" + addr + "]";
	}

}
