package com.seezoon.eagle.lock.demo;

import org.junit.Test;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.eagle.core.test.BaseJunitTest;

/**
 *
 * 
 * 读写锁
 * @author hdf
 *
 */
public class ReadWriteLockTest extends BaseJunitTest{
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Test
	public void lock() throws InterruptedException{
		final RReadWriteLock lock = redissonClient.getReadWriteLock("lock");
        //读写互斥，所以这个例子是死锁
        lock.writeLock().lock();
         logger.info("write lock ok ...");
        Thread t = new Thread() {
            public void run() {
                 RLock r = lock.readLock();
                 r.lock();
                 logger.info("get lock ok ...");
                 try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                r.unlock();
            };
        };
        t.start();
        t.join();

        lock.writeLock().unlock();
        logger.info("write lock unlock ...");
        t.join();
	}
}
