package com.seezoon.eagle.lock.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.eagle.core.test.BaseJunitTest;

/**
 *  公平锁测试
 * 
 * 公平锁（Fair）：加锁前检查是否有排队等待的线程，优先排队等待的线程，先来先得
	非公平锁（Nonfair）：加锁时不考虑排队等待问题，直接尝试获取锁，获取不到自动到队尾等待
 * @author hdf
 *
 */
public class FairLockTest extends BaseJunitTest{
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Test
	public void lock() throws InterruptedException{
		final RLock lock = redissonClient.getFairLock("lock");

        int size = 10;
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < size; i++) {
            final int j = i;
            Thread t = new Thread() {
                public void run() {
                    lock.lock();
                    lock.unlock();
                };
            };
            
            threads.add(t);
        }
        
        for (Thread thread : threads) {
            thread.start();
            thread.join(5);
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
	}
}
