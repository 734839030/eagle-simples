package com.seezoon.eagle.conf.demo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.eagle.conf.demo.service.DemoService;
import com.seezoon.eagle.core.test.BaseJunitTest;

/**
 * 分布式配置演示
 * @author hdf
 *
 */
public class DemoConfTest extends BaseJunitTest{
	
	@Autowired
	private DemoService demoService;
	@Test
	public void test(){
		while (true) {
			try {
				Thread.sleep(10000);
				demoService.print();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
