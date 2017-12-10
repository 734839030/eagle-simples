package com.seezoon.eagle.mybatis.demo.entity;

import java.util.Date;

import com.seezoon.eagle.mybatis.dao.BaseEntity;

public class Student extends BaseEntity{
	
    private Integer id;

    private String name;

    private Integer age;

    private Date birth;

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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