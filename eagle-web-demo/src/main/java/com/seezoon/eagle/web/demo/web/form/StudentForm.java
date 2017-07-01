package com.seezoon.eagle.web.demo.web.form;

import org.hibernate.validator.constraints.NotEmpty;

public class StudentForm {
	@NotEmpty
	private String userName;
	private Integer age;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
