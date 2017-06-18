package com.seezoon.eagle.redis.demo.dao;

import javax.annotation.Resource;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.seezoon.eagle.redis.demo.entity.Student;
import com.seezoon.eagle.redis.queue.AbstractRedisQueue;

@Component
public class DemoQueue extends AbstractRedisQueue<String, Student>{
	
	@Resource(name="redisTemplate")
	private ListOperations<String, Student> ListOps;
	
	@Override
	public void handleMessage(Student value) {
		logger.debug(JSON.toJSONString(value));
	}

	@Override
	public String getKeyName() {
		return "list1";
	}

	@Override
	public long getTimeout() {
		return 1000;
	}

	@Override
	public ListOperations<String, Student> getListOperations() {
		return ListOps;
	}

	
}
