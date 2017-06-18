package com.seezoon.eagle.rocketmq.demo;

import java.util.List;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

public class DemoMessageQueueSelector implements MessageQueueSelector {

	@Override
	public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
		Integer id = (Integer) arg;
        int index = id % mqs.size();        
        return mqs.get(index);
	}

}
