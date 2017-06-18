package com.seezoon.eagle.amq.demo.listener;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.seezoon.eagle.amq.listener.AbstractListener;

/**

 * 按需复写父类方法，真实业务请直接继承AbstractListener

 * topic 监听器

 * @author DF

 *

 */
@Component
public class TopicListener extends AbstractListener{
	@Override
	public void textMessageHander(String text) {
		logger.debug(text);
	}
	@Override
	public <V> void mapMessageHander(Map<String, V> map) {
		logger.debug(JSON.toJSONString(map));
	}
}