package com.seezoon.eagle.mongo.demo.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "person") 
public class Person {
	@Id //可以省略 
	private String id;
	/*索引  符合索引CompoundIndex */
	@Indexed
	private String name;
	@Indexed
	private Integer age;
	private Date birth;
	private Boolean flag;
	@DBRef // 级联查询。 不会级联删除
	private List<Child> child;
	/*不入库*/
	@Transient
	private String test;
	
	
	public Person() {
		super();
	}
	public Person(String id, String name, Integer age, Date birth,
			Boolean flag, List<Child> child) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.birth = birth;
		this.flag = flag;
		this.child = child;
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
	public List<Child> getChild() {
		return child;
	}
	public void setChild(List<Child> child) {
		this.child = child;
	}
	
}
