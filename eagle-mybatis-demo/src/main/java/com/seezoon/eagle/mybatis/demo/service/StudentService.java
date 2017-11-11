package com.seezoon.eagle.mybatis.demo.service;

import com.github.pagehelper.PageInfo;
import com.seezoon.eagle.mybatis.demo.entity.Student;

/**
 * 
 * @author hdf
 *
 */
public interface StudentService  {

	public int insert(Student t);
	public Student selectByPrimaryKey(int id);
	public PageInfo<Student>  findByPage(Student t, int page, int maxResult);
}
