package com.seezoon.eagle.lock.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.eagle.core.test.BaseJunitTest;

/**
 * 计数器	
 * 
 * 
 * @author hdf
 *
 */
public class CountDownLatchTest extends BaseJunitTest{
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Test
	public void lock() throws InterruptedException{
		ExecutorService executor = Executors.newFixedThreadPool(2);
		final RCountDownLatch latch = redissonClient.getCountDownLatch("latch1");
	    latch.trySetCount(2);
	    executor.execute(new Runnable() {
            @Override
            public void run() {
                latch.countDown();
                logger.info("task 1");
            }
        });
	    executor.execute(new Runnable() {
            @Override
            public void run() {
                latch.countDown();
                logger.info("task 2");
            }
        });
	    latch.await();
	    logger.info("task all completed.");
	}
}
