package com.seezoon.eagle.redis.demo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.eagle.core.test.BaseJunitTest;
import com.seezoon.eagle.redis.sentinel.ShardedJedisSentinelPool;

import redis.clients.jedis.ShardedJedis;

public class TestSharedSentinelRedis extends BaseJunitTest{

	@Autowired
	ShardedJedisSentinelPool pool;
	
	@Test
	public void testShared(){
		ShardedJedis resource = pool.getResource();
		logger.info("idel:{},max:{}",pool.getNumIdle(),pool.getNumActive());
		for (int i = 0 ;i <100 ;i++) {
			resource.set("a"+ i, i+"");
		}
		resource.close();
	}
}
