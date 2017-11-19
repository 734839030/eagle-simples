package com.seezoon.eagle.rocketmq.demo.consumer;

import java.util.Date;

import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import com.seezoon.eagle.rocketmq.listener.AbstractMessageListenerOrderly;
/**
 * 更多demo 参见官方
 * https://github.com/apache/rocketmq/tree/master/example/src/main/java/org/apache/rocketmq/example
 * @author hdf
 * 2017年11月18日
 */
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
