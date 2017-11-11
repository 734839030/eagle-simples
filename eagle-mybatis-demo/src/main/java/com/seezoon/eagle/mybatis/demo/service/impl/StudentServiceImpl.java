package com.seezoon.eagle.mybatis.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.seezoon.eagle.core.service.BaseService;
import com.seezoon.eagle.mybatis.demo.dao.StudentDao;
import com.seezoon.eagle.mybatis.demo.entity.Student;
import com.seezoon.eagle.mybatis.demo.service.StudentService;

@Service
public class StudentServiceImpl  extends BaseService  implements StudentService{
	@Autowired
	private StudentDao studentDao;
	@Override
	public int insert(Student t) {
		return studentDao.insert(t);
	}

	@Override
	public Student selectByPrimaryKey(int id) {
		return studentDao.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<Student> findByPage(Student t, int page, int maxResult) {
		return studentDao.findByPage(t, page, maxResult);
	}

}
