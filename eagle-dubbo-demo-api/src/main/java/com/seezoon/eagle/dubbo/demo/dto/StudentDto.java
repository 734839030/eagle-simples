package com.seezoon.eagle.dubbo.demo.dto;

import java.util.Date;

public class StudentDto extends CommonDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String name;
	private int age;
	private Date birthDay;
	private String sex;
	public StudentDto() {
		super();
	}
	public StudentDto(String name, int age, Date birthDay, String sex) {
		super();
		this.name = name;
		this.age = age;
		this.birthDay = birthDay;
		this.sex = sex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
