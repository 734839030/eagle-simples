## eagle-dubbbo provider的例子
#####   例子包含功能 

1.采用dubbo 自带 com.alibaba.dubbo.container.Main 启动类（可以实现优雅关机），maven 打包后直接可以jar -jar 运行dubbo服务，生产推荐使用。

```
java -jar eagle-dubbo-demo-provider.jar 
```

```
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-jar-plugin</artifactId>
	<version>2.4</version>
	<configuration>
		<classesDirectory>target/classes/</classesDirectory>
		<archive>
			<manifest>
				<mainClass>com.alibaba.dubbo.container.Main</mainClass>
				<!-- 打包时 MANIFEST.MF文件不记录的时间戳版本 -->
				<useUniqueVersions>false</useUniqueVersions>
				<addClasspath>true</addClasspath>
				<classpathPrefix>lib/</classpathPrefix>
			</manifest>
			<manifestEntries>
				<Class-Path>.</Class-Path>
			</manifestEntries>
		</archive>
	</configuration>
</plugin>
```
开发的时候测试用例继承DubboJunitTest 可以发布dubbo服务。

2.整合内嵌jetty ，利用spring mvc 发布restful 服务（dubbox resteasy 很难用，性能也没有springmvc 好 ）。***测试***阶段可以使用如下命令启动dubbo rest服务。

```
java -jar eagle-dubbo-demo-provider.jar jetty1 
```
jetty1 为eagel-dubbo 中遵循dubbo SPI机制扩展的Container.

开发环境测试类继承DubboHttpTest即可发布dubbo，rest 服务

内嵌的jetty 不建议用于生产环境，如果需要生产环境，可以将eagle-dubbo-demo-provider.jar放入servlet 容器中，以tomcat 为例
放入webapp/ROOT/WEB-INF/lib 中,同时WEB-INF/web.xml 配置如

```
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath*:spring-context*.xml</param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <listener>
        <listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>  
  </listener>
  
  <filter>
  	<filter-name>WebFilter</filter-name>
  	<filter-class>com.seezoon.eagle.dubbo.filter.WebFilter</filter-class>
  </filter>
  <filter-mapping>
  	<filter-name>WebFilter</filter-name>
  	<url-pattern>/*</url-pattern>
  </filter-mapping>
  
  <filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <!-- MVC Servlet -->
  <servlet>
    <servlet-name>springServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath*:spring-mvc*.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>springServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
```

启动tomcat 就可以发布dubbo，rest。多个工程jar 放入都可以。


以下开源工具推荐使用

dubbo-admin 

dubbo-monitor 

zk 的监控推荐taokeeper

rest 服务推荐用kong 做网关。


## eagle-mybatis 的例子

```
具体使用见src/test/java
```

主要使用eagel-mybatis 提供的CrudService，分页工具类采用开源PageHelper

```
/**
 * 当需要自动拥有增删改查功能时候继承
 * @author hdf
 *
 */
@Transactional(rollbackFor=Exception.class)
public abstract class CrudService<D extends CrudDao<T>,T extends BaseEntity>{

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected D dao; 
	
	public int insert(T t){
		return dao.insert(t);
	}
	public int insertSelective(T t){
		return dao.insert(t);
	}
	public int updateByPrimaryKeySelective(T t){
		return dao.updateByPrimaryKeySelective(t);
	}
	public int updateByPrimaryKey(T t){
		return dao.updateByPrimaryKey(t);
	}
	@Transactional(readOnly=true)
	public T selectByPrimaryKey(Serializable id){
		return dao.selectByPrimaryKey(id);
	}
	public int deleteByPrimaryKey(Serializable id){
		return dao.deleteByPrimaryKey(id);
	}
	@Transactional(readOnly=true)
	public List<T> findList(T t){
		return dao.findList(t);
	}
	@Transactional(readOnly=true)
	public PageInfo<T> findByPage(T t,int pageNum,int pageSize,boolean count){
		PageHelper.startPage(pageNum, pageSize, count);
		List<T>  list = this.findList(t);
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		return pageInfo;
	}
	@Transactional(readOnly=true)
	public PageInfo<T> findByPage(T t,int pageNum,int pageSize){
		PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
		List<T>  list = this.findList(t);
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		return pageInfo;
	}
}
```

## eagle-mongo 的例子

```
具体使用见src/test/java
```

例子使用eagle-mongo 封装的DAO,采用spring-data-mongo 中提供的MongoTemplate，分页工具类采用开源PageHelper

```
public abstract class MongoBaseDao<T> {
	
	protected  Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected MongoTemplate mongoTemplate;

	/**

	 * 保存或更新一个对象，

	 * @param t

	 * @return

	 */
	public void saveOrUpdate(T t) {
		this.mongoTemplate.save(t);
	}
	
	/**

	 * 保存一个对象

	 * @param t

	 */
	public void insert(T t){
		this.mongoTemplate.insert(t);
	}

	/**

	 * 根据Id从Collection中查询对象

	 * @return

	 */
	public T queryById(String id) {
		Query query = new Query();
		Criteria criteria = Criteria.where("_id").is(id);
		query.addCriteria(criteria);
		return this.mongoTemplate.findOne(query, this.getEntityClass());
	}

	/**

	 * 根据条件查询集合

	 * @param query 查询条件

	 * @return 满足条件的集合

	 */
	public List<T> queryList(Query query) {
		return this.mongoTemplate.find(query, this.getEntityClass());
	}

	/**

	 * 通过条件查询单个实体，有一个结果的话取第一个

	 * @param query

	 * @return

	 */
	public T queryOne(Query query) {
		return this.mongoTemplate.findOne(query, this.getEntityClass());
	}

	

	/**

	 * 根据条件查询库中符合记录的总数,为分页查询服务

	 * @param query 查询条件

	 * @return 满足条件的记录总数

	 */
	public Long getPageCount(Query query) {
		return this.mongoTemplate.count(query, this.getEntityClass());
	}
	/**

	 * 通过条件进行分页查询

	 * @param query 查询条件

	 * @param pageNum 查询起始值 ，

	 * @param pageSize  查询大小 ，类似mysql查询中的 limit start, size 中的 size

	 * @return 满足条件的集合

	 */
	public PageInfo<T> getPage(Query query, int pageNum,int pageSize){
		Page<T> page = new Page<T>(pageNum, pageSize);
		query.skip(page.getStartRow());
		query.limit(page.getPageSize());
		List<T> lists = this.mongoTemplate.find(query, this.getEntityClass());
		PageInfo<T> pageInfo = new PageInfo<T>(lists);
		pageInfo.setTotal(this.getPageCount(query));
		return pageInfo;
	}
	/**

	 * 根据Id删除用户

	 * @param id

	 */
	 public void deleteById(String id) {
		 Criteria criteria = Criteria.where("_id").in(id);
		 if(null!=criteria){
			 Query query = new Query(criteria);
			 if(null!=query && this.queryOne(query)!=null){
				 this.mongoTemplate.remove(query, this.getEntityClass());
			 }
		 }
	 }
	 
	 /**

	  * 根据条件删除

	  * @param query

	  */
	 public void delete(Query query){
		 this.mongoTemplate.remove(query, this.getEntityClass());
	 }

	/**

	 * 删除对象

	 * @param t

	 */
	 public void delete(T t){
		 this.mongoTemplate.remove(t);
	 }

	/**

	 * 更新满足条件的第一个记录

	 * @param query

	 * @param update

	 */
	 public void updateFirst(Query query,Update update){
		 this.mongoTemplate.updateFirst(query, update, this.getEntityClass());
	 }

	/**

	 * 更新满足条件的所有记录

	 * @param query

	 * @param update

	 */
	 public void updateMulti(Query query, Update update){
		 this.mongoTemplate.updateMulti(query, update, this.getEntityClass());
	 }

	/**

	 * 查找更新,如果没有找到符合的记录,则将更新的记录插入库中

	 * @param query

	 * @param update

	 */
	 public void updateInser(Query query, Update update){
		 this.mongoTemplate.upsert(query, update, this.getEntityClass());
	 }

	/**

	 * 钩子方法,由子类实现返回反射对象的类型

	 * @return

	 */
	protected  Class<T> getEntityClass(){
		return GenericsUtils.getSuperClassGenricType(this.getClass());
	};
```

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


## eagle-rocketmq 的例子

```
具体使用见src/test/java
```

几种消息发布

```
/**
	 * 同步发确保直接返回回执
	 * @throws 
	 */
	@Test
	public void send() {
		Message msg = new Message("TopicTest", "hello rocketmq".getBytes()) ;
		msg.putUserProperty("threadId", NDCUtils.push());
		try {
			SendResult send = defaultMQProducer.send(msg);
			logger.debug("DefaultMQProducer send:{}",JSON.toJSONString(send));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	/**
	 * 不关心发送结果
	 */
	@Test
	public void  sendOneway(){
		Message msg = new Message("TopicTest", "hello rocketmq".getBytes()) ;
		try {
			defaultMQProducer.sendOneway(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	/**
	 * 关心发送结果
	 */
	@Test
	public void  sendCallback(){
		Message msg = new Message("TopicTest", "hello rocketmq".getBytes()) ;
		try {
			defaultMQProducer.send(msg,new SendCallback() {
				
				@Override
				public void onSuccess(SendResult sendResult) {
					logger.debug("sendCallback onSuccess :{}",JSON.toJSONString(sendResult));
				}
				
				@Override
				public void onException(Throwable e) {
					logger.error(e.getMessage());
				}
			});
			System.in.read();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	/**
	 * 有序消息 接收需要用MessageListenerOrderly  保证发入一个queue 就可以 利用MessageQueueSelector
	 */
	@Test
	public void sendOrdered(){
		Message msg = new Message("TopicTest", "hello rocketmq1".getBytes()) ;
		MessageQueueSelector messageQueueSelector = new DemoMessageQueueSelector();
		try {
			defaultMQProducer.send(msg, messageQueueSelector, 1);
			defaultMQProducer.send(new Message("TopicTest", "hello rocketmq2".getBytes()) , messageQueueSelector, 1);
			defaultMQProducer.send(new Message("TopicTest", "hello rocketmq3".getBytes()) , messageQueueSelector, 1);
			defaultMQProducer.send(new Message("TopicTest", "hello rocketmq4".getBytes()) , messageQueueSelector, 1);
			defaultMQProducer.send(new Message("TopicTest", "hello rocketmq5".getBytes()) , messageQueueSelector, 1);
			System.in.read();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
```
接受例子见src/main/java
