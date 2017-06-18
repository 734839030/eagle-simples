package com.seezoon.dubbo.demo.consumer;


import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.seezoon.eagle.dubbo.demo.api.IStudentService;
import com.seezoon.eagle.dubbo.demo.dto.StudentDto;
import com.seezoon.eagle.dubbo.test.DubboJunitTest;

public class StudentServiceTest extends DubboJunitTest {

	@Autowired
	private IStudentService studentService;
	@Test
	public void testGetByName() {
		StudentDto stu = studentService.getByName("11");
		logger.info("testGetByName:{}",JSON.toJSONString(stu));
	}

	@Test
	public void testAddStudent() {
		StudentDto stu = new StudentDto("stu101", 11, null, "女");
		boolean addStudent = studentService.addStudent(stu);
		logger.info("addStudent:{}",JSON.toJSONString(addStudent));
	}

	@Test
	public void testGetByAgeRange() {
		List<StudentDto> byAgeRange = studentService.getByAgeRange(1, 100);
		logger.info("byAgeRange:{}",JSON.toJSONString(byAgeRange));
	}

}
