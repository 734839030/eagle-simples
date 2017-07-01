package com.seezoon.eagle.web.demo.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.seezoon.eagle.web.core.BaseController;
import com.seezoon.eagle.web.demo.web.form.StudentForm;

@Controller
@RequestMapping(value="/demo")
public class DemoController extends BaseController{

	
	@ResponseBody
	@RequestMapping(value="/hello")
	public Map<String,String> hello(){
		Map<String, String> data = new HashMap<>();
		data.put("eagle-web", "hello");
		return data;
	}
	@ResponseBody
	@RequestMapping(value="/validate")
	public Map<String,String> validate(@Valid StudentForm form,BindingResult result){
		Map<String, String> data = new HashMap<>();
		data.put("eagle-web", "hello");
		return data;
	}
}
