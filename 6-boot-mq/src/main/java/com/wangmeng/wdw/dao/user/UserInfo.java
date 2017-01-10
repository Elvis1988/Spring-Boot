package com.wangmeng.wdw.dao.user;

import java.io.Serializable;

public class UserInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private String sex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return String.format("{id=%s, name=%s, sex=%s}", id, name, sex);
	}
	
	

}
