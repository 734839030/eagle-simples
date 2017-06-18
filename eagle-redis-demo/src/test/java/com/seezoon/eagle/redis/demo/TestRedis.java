package com.seezoon.eagle.redis.demo;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson.JSON;
import com.seezoon.eagle.core.test.BaseJunitTest;
import com.seezoon.eagle.redis.demo.entity.Student;

public class TestRedis extends BaseJunitTest{
	
	@Autowired
	private RedisTemplate<String,String> redisTemplateStr;
	
	@Autowired
	private RedisTemplate<String,Student> redisTemplateStu;
	
	@Resource(name="redisTemplate")
	private ValueOperations<String, String> valueOps;
	@Resource(name="redisTemplate")
	private ListOperations<String, Student> listOps;
	@Resource(name="redisTemplate")
	private SetOperations<String, Student> setOps;
	@Resource(name="redisTemplate")
	private HashOperations<String, String, Student> hashOps;
	@Test
	public void redisTemplateStr(){
		redisTemplateStr.opsForValue().set("123", "1223");
		String value = redisTemplateStr.opsForValue().get("123");
		logger.debug(JSON.toJSONString(value));
	}
	@Test
	public void redisTemplateStu(){
		redisTemplateStu.opsForValue().set("s1", new Student("a", 12, new Date()), 10, TimeUnit.MINUTES);
		Student student = redisTemplateStu.opsForValue().get("s1");
		logger.debug(JSON.toJSONString(student));
	}
	@Test
	public void valueOps(){
		valueOps.set("123", "1223");
		String value = valueOps.get("123");
		logger.debug(JSON.toJSONString(value));
	}
	@Test
	public void listOps(){
		listOps.leftPush("list1", new Student("a", 12, new Date()));
		List<Student> list = listOps.range("list1",0, 1);
		logger.debug(JSON.toJSONString(list));
		Student leftPop = listOps.leftPop("list1");
		logger.debug(JSON.toJSONString(leftPop));
		listOps.leftPush("list1", new Student("a", 13, new Date()));
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void setOps(){
		setOps.add("set1", new Student("a", 12, new Date()));
		setOps.add("set2", new Student("a", 12, new Date()));
		logger.debug(JSON.toJSONString(setOps.members("set1")));
		logger.debug(JSON.toJSONString(setOps.pop("set1")));
	}
	@Test
	public void hashOps(){
		hashOps.put("hash1", "hash1", new Student("a", 12, new Date()));
		Student student = hashOps.get("hash1","hash1");
		logger.debug(JSON.toJSONString(student));
	}
	/**
	 * 发布消息
	 */
	@Test
	public void publisMsg(){
		redisTemplateStu.convertAndSend("chatroom", new Student("a", 12, new Date()));
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 官方推荐的事务的处理方式,也可以用@transaction
	 */
	@Test
	public void transaction(){
		//execute a transaction
		List<Object> txResults = redisTemplateStr.execute(new SessionCallback<List<Object>>() {
		  public List<Object> execute(RedisOperations operations) throws DataAccessException {
		    operations.multi();
		    operations.opsForSet().add("key", "value1");
		    // This will contain the results of all ops in the transaction
		    return operations.exec();
		  }
		});
		System.out.println("Number of items added to set: " + txResults.get(0));
	}
	
}
