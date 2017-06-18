package com.seezoon.eagle.rocketmq.demo.consumer;

import java.util.Date;

import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import com.seezoon.eagle.rocketmq.listener.AbstractMessageListenerConcurrently;

@Component
public class DemoConcurrentlyMessageListener extends AbstractMessageListenerConcurrently {
	
	@Override
	public boolean handleMessage(MessageExt messageExt) {
		try {
			logger.debug("DemoConcurrentlyMessageListener consume  msgId :{},borntime:{},content:{}",messageExt.getMsgId(), new Date(messageExt.getBornTimestamp()), new String(messageExt.getBody()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
