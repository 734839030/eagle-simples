package com.seezoon.eagle.httpclient.demo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.ParseException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seezoon.eagle.httpclient.HttpClientServiceBean;


/**
 * Unit test for simple App.
 */
public class AppTest{
	private static Logger logger = LoggerFactory.getLogger(AppTest.class);
	@Test
	public void t1() throws MalformedURLException{
		URL url = new URL("http://www.baidu.com/1");
		System.out.println(url.getProtocol());
	}
	@Test
	public void t2() throws ParseException, IOException{
		HttpClientServiceBean bean = new HttpClientServiceBean();
		Map<String,String> map = new HashMap<String,String>();
		map.put("a", "a");
		String httpGet = bean.httpPost("http://localhost:8080/seezoon-admin/sys/dict/getDictByType?type=sys_dict", map, "UTF-8");
		System.out.println(httpGet);
	}
	@Test
	public void t3() throws ParseException, IOException{
		HttpClientServiceBean bean = new HttpClientServiceBean();
		for (int i = 0;i<1000;i++) {
			String httpGet = bean.httpGet("https://bank.pingan.com.cn/ibp/ibp4pc/login.do", null, "UTF-8");
			logger.debug(httpGet);
		}
	}
}
