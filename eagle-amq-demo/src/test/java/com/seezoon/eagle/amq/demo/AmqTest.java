package com.seezoon.eagle.amq.demo;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.eagle.amq.demo.sender.QueueSender;
import com.seezoon.eagle.amq.demo.sender.TopicSender;
import com.seezoon.eagle.core.test.BaseJunitTest;

/**
 * 测试 amq 也是支持spring 申明式事务的
 * @author hdf
 *
 */
public class AmqTest extends BaseJunitTest{

	@Autowired
	private QueueSender queueSender;
	@Autowired
	private TopicSender topicSender;
	@Test
	public void testQueue(){
		queueSender.sendTextMessage("test.queue", "收不到消息");
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("1", "map message test");
		queueSender.sendMapMessage("test.queue", map);
		queueSender.sendObjectMessage("test.queue", map);
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	}
	@Test
	public void testTopic(){
		topicSender.sendTextMessage("test.topic", "你好");
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("1", "map message test");
		topicSender.sendMapMessage("test.topic", map);
		topicSender.sendObjectMessage("test.topic", map);
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	}
}
