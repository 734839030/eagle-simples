package com.seezoon.eagle.lock.demo;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.eagle.core.test.BaseJunitTest;

/**
 * 最基本的锁测试
 * 
 * 
 * @author hdf
 *
 */
public class ReentrantLockTest extends BaseJunitTest{
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Test
	public void lock(){
	     //实例化一把锁 
 		 RLock lock = redissonClient.getLock("lock");
 		 try {
 			 //api 和java 原生类似，lock  tryLock等
 			 lock.lock();
 			 //lock.lock(10, TimeUnit.SECONDS); 持有10 s 最好采用这种内部
 			 //lock.tryLock();  如果拿到锁返回true 
 			 //lock.tryLock(10, TimeUnit.SECONDS);//10s 内拿到锁返回true
 			 //doSomething
 			 logger.info("do something...");
 		 }  finally {
 			lock.unlock();
		}
	}
}
