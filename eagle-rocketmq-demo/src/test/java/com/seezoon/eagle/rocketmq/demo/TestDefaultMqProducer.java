package com.seezoon.eagle.rocketmq.demo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.seezoon.eagle.core.test.BaseJunitTest;
import com.seezoon.eagle.core.utils.NDCUtils;
import com.seezoon.eagle.rocketmq.demo.transaction.TransactionCheckListenerImpl;
import com.seezoon.eagle.rocketmq.demo.transaction.TransactionExecuterImpl;

public class TestDefaultMqProducer extends BaseJunitTest{

	@Autowired
	private DefaultMQProducer defaultMQProducer;
	/**
	 * 同步发确保直接返回回执
	 * @throws 
	 */
	@Test
	public void send() {
		Message msg = new Message("TopicTest", "hello rocketmq".getBytes()) ;
		msg.putUserProperty("threadId", NDCUtils.peek());
		try {
			SendResult send = defaultMQProducer.send(msg);
			logger.debug("DefaultMQProducer send:{}",JSON.toJSONString(send));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	/**
	 * 不关心发送结果
	 */
	@Test
	public void  sendOneway(){
		Message msg = new Message("TopicTest", "hello rocketmq".getBytes()) ;
		try {
			defaultMQProducer.sendOneway(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	/**
	 * 异步发送，关心发送结果
	 */
	@Test
	public void  sendCallback(){
		Message msg = new Message("TopicTest", "hello rocketmq".getBytes()) ;
		try {
			defaultMQProducer.send(msg,new SendCallback() {
				
				@Override
				public void onSuccess(SendResult sendResult) {
					logger.debug("sendCallback onSuccess :{}",JSON.toJSONString(sendResult));
				}
				
				@Override
				public void onException(Throwable e) {
					logger.error(e.getMessage());
				}
			});
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 有序消息 接收需要用MessageListenerOrderly  保证发入一个queue 就可以 利用MessageQueueSelector
	 */
	@Test
	public void sendOrdered(){
		Message msg = new Message("TopicTest", "hello rocketmq1".getBytes()) ;
		MessageQueueSelector messageQueueSelector = new DemoMessageQueueSelector();
		try {
			defaultMQProducer.send(msg, messageQueueSelector, 1);
			defaultMQProducer.send(new Message("TopicTest", "hello rocketmq2".getBytes()) , messageQueueSelector, 1);
			defaultMQProducer.send(new Message("TopicTest", "hello rocketmq3".getBytes()) , messageQueueSelector, 1);
			defaultMQProducer.send(new Message("TopicTest", "hello rocketmq4".getBytes()) , messageQueueSelector, 1);
			defaultMQProducer.send(new Message("TopicTest", "hello rocketmq5".getBytes()) , messageQueueSelector, 1);
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 有序消息 接收需要用MessageListenerOrderly  保证发入一个queue 就可以 利用MessageQueueSelector
	 */
	@Test
	public void sendTagMsg(){
		Message msg = new Message("TopicTest", "hello rocketmq tag".getBytes()) ;
		msg.setTags("mytag");
		try {
			defaultMQProducer.sendOneway(msg);
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 批量发送4.1 + 版本
	 * @throws MQClientException
	 * @throws RemotingException
	 * @throws MQBrokerException
	 * @throws InterruptedException
	 */
	@Test
	public void sendBatch() throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		String topic = "BatchTest";
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "Tag", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message(topic, "Tag", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message(topic, "Tag", "OrderID003", "Hello world 2".getBytes()));
        SendResult send = defaultMQProducer.send(messages);
	}
	@Test
	public void transactionMsg() throws Exception {
		TransactionCheckListener transactionCheckListener = new TransactionCheckListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("192.168.221.133:9876");
        producer.setCheckThreadPoolMinSize(2);
        producer.setCheckThreadPoolMaxSize(2);
        producer.setCheckRequestHoldMax(2000);
        producer.setTransactionCheckListener(transactionCheckListener);
        producer.start();
        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
        TransactionExecuterImpl tranExecuter = new TransactionExecuterImpl();
        for (int i = 0; i < 100; i++) {
            try {
                Message msg =
                    new Message("TopicTest1", tags[i % tags.length], "KEY" + i,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction(msg, tranExecuter, null);
                System.out.printf("%s%n", sendResult);

                Thread.sleep(1000);
            } catch (MQClientException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
        }
        producer.shutdown();
	}
}
