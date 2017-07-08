package com.seezoon.eagle.lock.demo;

import org.junit.Test;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.eagle.core.test.BaseJunitTest;

/**
 *  信号灯测试
 * 
 * 
 * @author hdf
 *
 */
public class SemaphoreTest extends BaseJunitTest{
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Test
	public void lock() throws InterruptedException{
		 final RSemaphore s = redissonClient.getSemaphore("lock");
	        s.trySetPermits(5);
	        s.acquire(3);
	        Thread t = new Thread() {
	            @Override
	            public void run() {
	            	logger.info("start release 2");
	                s.release();
	                s.release();
	                logger.info("release 2");
	            }
	        };
	        t.start();
	        s.acquire(4);
	        s.release(5);
	        logger.info("main 5");
	}
}
