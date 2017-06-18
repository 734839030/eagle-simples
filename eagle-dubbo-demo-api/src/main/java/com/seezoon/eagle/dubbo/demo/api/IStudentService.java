package com.seezoon.eagle.dubbo.demo.api;

import java.util.List;

import com.seezoon.eagle.dubbo.demo.dto.StudentDto;

public interface IStudentService {

	public StudentDto getByName(String name);
	public StudentDto getByName(Integer age);
	public boolean addStudent(StudentDto studentDto);
	public List<StudentDto> getByAgeRange(int min,int max);
}
