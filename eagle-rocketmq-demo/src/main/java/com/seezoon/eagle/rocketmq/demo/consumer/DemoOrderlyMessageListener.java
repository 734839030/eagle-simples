package com.seezoon.eagle.rocketmq.demo.consumer;

import java.util.Date;

import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import com.seezoon.eagle.rocketmq.listener.AbstractMessageListenerOrderly;

@Component
public class DemoOrderlyMessageListener extends AbstractMessageListenerOrderly {

	@Override
	public boolean handleMessage(MessageExt messageExt) {
		try {
			logger.debug("DemoOrderlyMessageListener consume  msgId :{},borntime:{},content:{}",messageExt.getMsgId(), new Date(messageExt.getBornTimestamp()), new String(messageExt.getBody()));
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return true;
	}

}
