## eagle-lock 分布式锁 的例子

```
具体使用见src/test/java
```

基于[redisession](https://github.com/redisson/redisson/wiki/目录) 实现

模拟java concurrent 包下常用同步工具

## 配置具体参数 [配置方法](https://github.com/redisson/redisson/wiki/2.-配置方法#24-集群模式)  

```
<redisson:client>
		<!-- 单机配置 -->
 	    <redisson:single-server address="${redis.address}"  connect-timeout="${redis.connect.timeout}"
 	    connection-pool-size="${redis.pool.size}" connection-minimum-idle-size="${redis.idle.size}" timeout="${redis.timeout}"
 	    />
 	<!--  集群环境
    <redisson:cluster-servers address>
        <redisson:node-address value="redis://127.0.0.1:6379" />
        <redisson:node-address value="redis://127.0.0.1:6380" />
        <redisson:node-address value="redis://127.0.0.1:6381" />
    </redisson:cluster-servers> -->   
    </redisson:client>
    
```

## 重入锁

```
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

```

## 计数器

```
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

```

## 信号灯

```
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
```

## 多重锁

```
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
	
```


## 读写锁

```
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
	
```

## 公平锁

```
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

```