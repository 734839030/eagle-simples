package com.seezoon.eagle.session.demo.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.seezoon.eagle.session.demo.web.form.StudentForm;
import com.seezoon.eagle.web.core.BaseController;

@Controller
@RequestMapping(value="/demo")
public class DemoController extends BaseController{

	@ResponseBody
	@RequestMapping(value="/set")
	public Map<String,String> set(HttpSession session){
		logger.debug("start set ");
		StudentForm form = new StudentForm();
		form.setAge(11);
		form.setUserName("huangdengfeng");
		StopWatch s1 = new StopWatch();
				s1.start();
		session.setAttribute("test", form);
		s1.stop();
		logger.debug("set time:{}",s1.getTotalTimeMillis());
		Map<String, String> data = new HashMap<>();
		data.put("eagle-session", "hello");
		return data;
	}
	@ResponseBody
	@RequestMapping(value="/get")
	public Map<String,String> get(HttpSession session){
		logger.debug("start get ");
		Map<String, String> data = new HashMap<>();
		data.put("eagle-session", "hello");
		StopWatch s1 = new StopWatch();
		s1.start();
		StudentForm form = (StudentForm)session.getAttribute("test");
		logger.debug("form:{}",JSON.toJSONString(form));
		s1.stop();
		logger.debug("get time:{}",s1.getTotalTimeMillis());
		return data;
	}
}
