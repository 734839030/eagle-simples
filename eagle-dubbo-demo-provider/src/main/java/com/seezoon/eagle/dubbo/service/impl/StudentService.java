package com.seezoon.eagle.dubbo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.seezoon.eagle.core.service.BaseService;
import com.seezoon.eagle.core.utils.MDCUtils;
import com.seezoon.eagle.dubbo.demo.api.IStudentService;
import com.seezoon.eagle.dubbo.demo.dto.StudentDto;

@Controller
@Service
public class StudentService extends BaseService implements IStudentService {
	
	private static Map<String,StudentDto> datas = new HashMap<String,StudentDto>();
	static {
		for (int i = 0; i < 100; i++) {
			StudentDto stu = new StudentDto("stu" + i, i, new Date(), "男");
			datas.put(stu.getName(), stu);
		}
	}
	
	@ResponseBody
	@RequestMapping("/getByName")
	@Override
	public StudentDto getByName(String name) {
		return  datas.get(name);
	}
	@Override
	public boolean addStudent(StudentDto studentDto) {
		if (null != studentDto) {
			datas.put(studentDto.getName(), studentDto);
		} 
		return true;
	}
	@Override
	public List<StudentDto> getByAgeRange(int min, int max) {
		List<StudentDto> list = new ArrayList<StudentDto>();
		for (Entry<String, StudentDto> entry : datas.entrySet()) {
			int age = entry.getValue().getAge();
			if (age > min && age < max) {
				list.add(entry.getValue());
			}
		}
		return list;
	}

	/**
	 * 测试重载
	 */
	public StudentDto getByName(Integer age) {
		return null;
	}

}
