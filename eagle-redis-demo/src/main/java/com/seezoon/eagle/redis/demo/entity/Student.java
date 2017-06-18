package com.seezoon.eagle.redis.demo.entity;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable{

	private String name;
	private Integer age;
	private Date birth;
	
	
	public Student() {
		super();
	}
	public Student(String name, Integer age, Date birth) {
		super();
		this.name = name;
		this.age = age;
		this.birth = birth;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
}
