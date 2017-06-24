package com.seezoon.eagle.rocketmq.demo.consumer;

import java.util.Date;

import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import com.seezoon.eagle.rocketmq.listener.AbstractMessageListenerConcurrently;

@Component
public class DemoConcurrentlyMessageListener extends AbstractMessageListenerConcurrently {
	
	@Override
	public boolean handleMessage(MessageExt messageExt) {
		String tags = messageExt.getTags();
		if ("mytag".equals(tags)) {
			logger.debug("this is tag msg ,you can handle it by some other way.");
		}
		try {
			logger.debug("DemoConcurrentlyMessageListener consume  msgId :{},borntime:{},content:{}",messageExt.getMsgId(), new Date(messageExt.getBornTimestamp()), new String(messageExt.getBody()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
