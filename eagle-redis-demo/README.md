## eagle-redis 的例子

```
具体使用见src/test/java
```

#例子包含
1.连接redis-cluster,为了测试方便建议采用[docker redis cluster](https://hub.docker.com/r/tommy351/redis-cluster/)，其他的也可以，如果docker不会请加群 ***514685454***
自己搭建见楼主csdn  [redis-cluster搭建](http://blog.csdn.net/hdf734839030/article/details/52155815)

 使用spring-data RedisTemplate

```
<!-- 加载配置属性文件 按需加载 -->
	<context:property-placeholder ignore-unresolvable="true" location="classpath*:redis/redis.properties" />
	<bean id="redisClusterConfiguration" class="org.springframework.data.redis.connection.RedisClusterConfiguration">
		<property name="maxRedirects" value="${redis.maxRedirects}"></property>
		<property name="clusterNodes">
			<set>
				<bean class="org.springframework.data.redis.connection.RedisClusterNode">
					<constructor-arg name="host" value="${redis.host1}"></constructor-arg>
					<constructor-arg name="port" value="${redis.port1}"></constructor-arg>
				</bean>
				<bean class="org.springframework.data.redis.connection.RedisClusterNode">
					<constructor-arg name="host" value="${redis.host2}"></constructor-arg>
					<constructor-arg name="port" value="${redis.port2}"></constructor-arg>
				</bean>
				<bean class="org.springframework.data.redis.connection.RedisClusterNode">
					<constructor-arg name="host" value="${redis.host3}"></constructor-arg>
					<constructor-arg name="port" value="${redis.port3}"></constructor-arg>
				</bean>
				<bean class="org.springframework.data.redis.connection.RedisClusterNode">
					<constructor-arg name="host" value="${redis.host4}"></constructor-arg>
					<constructor-arg name="port" value="${redis.port4}"></constructor-arg>
				</bean>
				<bean class="org.springframework.data.redis.connection.RedisClusterNode">
					<constructor-arg name="host" value="${redis.host5}"></constructor-arg>
					<constructor-arg name="port" value="${redis.port5}"></constructor-arg>
				</bean>
				<bean class="org.springframework.data.redis.connection.RedisClusterNode">
					<constructor-arg name="host" value="${redis.host6}"></constructor-arg>
					<constructor-arg name="port" value="${redis.port6}"></constructor-arg>
				</bean>
			</set>
		</property>
	</bean>
	<bean id="jedisPoolConfig"   class="redis.clients.jedis.JedisPoolConfig">
		<property name="maxWaitMillis" value="${redis.maxWaitMillis}" /> 
        <property name="maxIdle" value="${redis.maxIdle}" /> 
		<property name="maxTotal" value="${redis.maxTotal}" /> 
   </bean>
	<bean id="jeidsConnectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory"  destroy-method="destroy">
		<constructor-arg ref="redisClusterConfiguration" />
		<constructor-arg ref="jedisPoolConfig" />
	</bean>
	<bean id="jdkSerializer"
        class="org.springframework.data.redis.serializer.JdkSerializationRedisSerializer" />
        
	<bean id="stringSerializer"
        class="org.springframework.data.redis.serializer.StringRedisSerializer" />
	
	<bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
		<property name="connectionFactory" ref="jeidsConnectionFactory" />
		<!-- 默认就是这个 -->
		<property name="defaultSerializer" ref="jdkSerializer" />
		<property name="keySerializer" ref="stringSerializer" />
		<property name="hashKeySerializer" ref="stringSerializer" />
		<!-- 默认不支持  官方推荐不开启,源码看着有bug 会导致链接永远不释放，最新版貌似没看到解决-->
		<property name="enableTransactionSupport" value="false" />
		
	</bean>
```
2.redis set list hash基础数据结构操作

```
@Test
	public void redisTemplateStr(){
		redisTemplateStr.opsForValue().set("123", "1223");
		String value = redisTemplateStr.opsForValue().get("123");
		logger.debug(JSON.toJSONString(value));
	}
	@Test
	public void redisTemplateStu(){
		redisTemplateStu.opsForValue().set("s1", new Student("a", 12, new Date()), 10, TimeUnit.MINUTES);
		Student student = redisTemplateStu.opsForValue().get("s1");
		logger.debug(JSON.toJSONString(student));
	}
	@Test
	public void valueOps(){
		valueOps.set("123", "1223");
		String value = valueOps.get("123");
		logger.debug(JSON.toJSONString(value));
	}
	@Test
	public void listOps(){
		listOps.leftPush("list1", new Student("a", 12, new Date()));
		List<Student> list = listOps.range("list1",0, 1);
		logger.debug(JSON.toJSONString(list));
		Student leftPop = listOps.leftPop("list1");
		logger.debug(JSON.toJSONString(leftPop));
		listOps.leftPush("list1", new Student("a", 13, new Date()));
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void setOps(){
		setOps.add("set1", new Student("a", 12, new Date()));
		setOps.add("set2", new Student("a", 12, new Date()));
		logger.debug(JSON.toJSONString(setOps.members("set1")));
		logger.debug(JSON.toJSONString(setOps.pop("set1")));
	}
	@Test
	public void hashOps(){
		hashOps.put("hash1", "hash1", new Student("a", 12, new Date()));
		Student student = hashOps.get("hash1","hash1");
		logger.debug(JSON.toJSONString(student));
	}
	
```
3.redis 作为队列使用,其实利用的是list结构

```
@Test
	public void listOps(){
		listOps.leftPush("list1", new Student("a", 12, new Date()));
		List<Student> list = listOps.range("list1",0, 1);
		logger.debug(JSON.toJSONString(list));
		Student leftPop = listOps.leftPop("list1");
		logger.debug(JSON.toJSONString(leftPop));
		listOps.leftPush("list1", new Student("a", 13, new Date()));
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
```
```
@Component
public class DemoQueue extends AbstractRedisQueue<String, Student>{
	
	@Resource(name="redisTemplate")
	private ListOperations<String, Student> ListOps;
	
	@Override
	public void handleMessage(Student value) {
		logger.debug(JSON.toJSONString(value));
	}

	@Override
	public String getKeyName() {
		return "list1";
	}

	@Override
	public long getTimeout() {
		return 1000;
	}

	@Override
	public ListOperations<String, Student> getListOperations() {
		return ListOps;
	}

	
}
```

4.redis 发布订阅例子

```
   /**
	 * 发布消息
	 */
	@Test
	public void publisMsg(){
		redisTemplateStu.convertAndSend("chatroom", new Student("a", 12, new Date()));
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
```

```
<!-- 以下演示订阅 -->
	<bean id="messageListener1" class="org.springframework.data.redis.listener.adapter.MessageListenerAdapter">
	  <!-- 注意消息的序列化要一致 -->
	  <property name="serializer" ref="jdkSerializer" ></property>
	  <constructor-arg>
	    <bean class="com.seezoon.eagle.redis.demo.dao.DemoSubscriber"/>
	  </constructor-arg>
	</bean>
	
	<bean id="redisContainer" class="org.springframework.data.redis.listener.RedisMessageListenerContainer">
	  <property name="connectionFactory" ref="jedisConnectionFactory"/>
	  <property name="messageListeners">
	    <map>
	      <entry key-ref="messageListener1">
	        <bean class="org.springframework.data.redis.listener.ChannelTopic">
	          <constructor-arg value="chatroom"></constructor-arg>
	        </bean>
	      </entry>
	    </map>
	  </property>
	</bean>
```

```
/**
 * 演示订阅者
 * @author hdf
 *
 */
public class DemoSubscriber implements RedisSubscriber<Student>{

	@Override
	public void handleMessage(Student value) {
		System.out.println(JSON.toJSONString(value));
	}

}

```
