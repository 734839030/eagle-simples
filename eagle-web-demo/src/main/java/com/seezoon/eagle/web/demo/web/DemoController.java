package com.seezoon.eagle.web.demo.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seezoon.eagle.web.core.BaseController;

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
}
