package com.seezoon.eagle.conf.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seezoon.eagle.conf.demo.config.DemoConfig;
import com.seezoon.eagle.core.service.BaseService;

@Service
public class DemoService extends BaseService{
	
	@Autowired
	private DemoConfig demoConfig;
	
	public void print(){
		logger.info("demoConfig-name:{}",demoConfig.getRname());
		logger.info("demoConfig-name:{}",demoConfig.getNrname());
	}
}
