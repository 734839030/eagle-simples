package com.seezoon.eagle.mybatis.demo.dao;

import org.springframework.stereotype.Repository;

import com.seezoon.eagle.mybatis.dao.CrudDao;
import com.seezoon.eagle.mybatis.demo.entity.Student;
import com.seezoon.eagle.mybatis.demo.mapper.StudentMapper;

@Repository
public class StudentDao extends CrudDao<StudentMapper, Student>{
    
}