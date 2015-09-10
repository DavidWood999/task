package com.wsy.domain.po;

import java.io.Serializable;
import java.util.Date;

import com.wsy.util.Ignore;
import com.wsy.util.PrimaryKey;
import com.wsy.util.Table;

@Table("t_user")
public class UserEntity implements Serializable {
	@Ignore
	private static final long serialVersionUID = 1L;

	/* ���� */
	@PrimaryKey
	private String uuid;

	/* ��ʵ���� */
	private String realname;

	/* ��¼�� */
	private String loginname;

	/* ���� */
	private String password;

	/* �Ա� */
	private String sex;
	
	/* �������� */
	private Date birthdate;

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

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "UserEntity [uuid=" + uuid + ", realname=" + realname
				+ ", loginname=" + loginname + ", password=" + password
				+ ", sex=" + sex + ", birthdate=" + birthdate + ", age=" + age
				+ "]";
	}

}
