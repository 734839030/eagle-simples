package com.seezoon.eagle.mybatis.demo.dao;

import org.springframework.stereotype.Repository;

import com.seezoon.eagle.mybatis.demo.entity.Student;
import com.seezoon.eagle.mybatis.persistence.CrudDao;

@Repository
public interface StudentDao extends CrudDao<Student>{
    
}