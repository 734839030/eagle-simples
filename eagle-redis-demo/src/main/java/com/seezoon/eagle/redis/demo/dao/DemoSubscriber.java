package com.seezoon.eagle.redis.demo.dao;

import com.alibaba.fastjson.JSON;
import com.seezoon.eagle.redis.demo.entity.Student;
import com.seezoon.eagle.redis.queue.RedisSubscriber;

/**
 * 演示订阅者
 * @author hdf
 *
 */
public class DemoSubscriber implements RedisSubscriber<Student>{

	@Override
	public void handleMessage(Student value) {
		System.out.println(JSON.toJSONString(value));
	}

}
