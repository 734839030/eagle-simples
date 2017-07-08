package com.seezoon.eagle.lock.demo;

import org.junit.Test;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.eagle.core.test.BaseJunitTest;

/**
 *
 * 
 * 多重锁
 * @author hdf
 *
 */
public class MultiLockTest extends BaseJunitTest{
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Test
	public void lock() throws InterruptedException{
	     //实例化多把锁 
 		final RLock lock1 = redissonClient.getLock("lock1");
 		final RLock lock2 = redissonClient.getLock("lock2");
 		final RLock lock3 = redissonClient.getLock("lock3");
        Thread t = new Thread() {
            public void run() {
            	RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);
                try {
                     lock.lock();
                     logger.info("thread t ge lock..");
                    Thread.sleep(3000);
                }  catch (Exception e) {
					// TODO: handle exception
				} finally {
                    lock.unlock();
				}
                logger.info("thread t unlock lock..");
            };
        };
        t.start();
        //让t 先跑1s
        t.join(1000);
        RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);
        try {
        	 logger.info("thread t try get lock..");
        	 lock.lock();
        	 logger.info("thread main get lock..");
		} finally {
			 lock.unlock();
		}
       
	}
}
