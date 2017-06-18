package com.seezoon.eagle.mybatis.demo.service.impl;

import org.springframework.stereotype.Service;

import com.seezoon.eagle.mybatis.demo.dao.StudentDao;
import com.seezoon.eagle.mybatis.demo.entity.Student;
import com.seezoon.eagle.mybatis.demo.service.StudentService;
import com.seezoon.eagle.mybatis.service.CrudService;

@Service
public class StudentServiceImpl  extends CrudService<StudentDao, Student>  implements StudentService{

}
