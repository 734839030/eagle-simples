package com.seezoon.eagle.mybatis.demo;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.seezoon.eagle.core.test.BaseJunitTest;
import com.seezoon.eagle.mybatis.demo.entity.Student;
import com.seezoon.eagle.mybatis.demo.service.StudentService;


public class StudentServiceTest extends BaseJunitTest {

	@Autowired
	private StudentService studentService;
	@Test
	public void insert(){
		Student stu = new Student();
		stu.setId(100);
		stu.setAge(12);
		stu.setName("hdf1");
		int cnt = studentService.insert(stu);
		Assert.assertTrue(cnt == 1);
	}
	
	@Test
	public void selectByPrimaryKey(){
		Student student = studentService.selectByPrimaryKey(8);
		logger.debug("selectByPrimaryKey:{}", JSON.toJSONString(student));
		
	}
	@Test
	public void findByPage(){
		Student student = new Student();
		student.setId(8);
		PageInfo<Student> pageInfo  = studentService.findByPage(student, 2, 2);
		logger.debug("findByPage:{}",JSON.toJSONString(pageInfo));
	}
}
