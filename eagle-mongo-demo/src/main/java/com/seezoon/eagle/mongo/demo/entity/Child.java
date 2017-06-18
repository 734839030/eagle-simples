package com.seezoon.eagle.mongo.demo.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "child") 
public class Child {
	@Id //可以省略 z
	private String id;
	/*索引  符合索引CompoundIndex */
	@Indexed
	private String name;
	@Indexed
	private Integer age;
	private Date birth;
	private Boolean flag;
	/*不入库*/
	@Transient
	private String test;
	
	
	public Child() {
		super();
	}
	public Child(String id, String name, Integer age, Date birth,
			Boolean flag) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.birth = birth;
		this.flag = flag;
		this.test="test";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
}
