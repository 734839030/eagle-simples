## eagle-session 的例子
eclipse 建立maven web项目 例子
http://www.cnblogs.com/noteless/p/5213075.html

采用spring-session 框架
--
具体配置web.xml ，放在所有filter 之前
```
<filter>
		<filter-name>springSessionRepositoryFilter</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>springSessionRepositoryFilter</filter-name>
		<url-pattern>/*</url-pattern>
		<dispatcher>REQUEST</dispatcher>
		<dispatcher>ERROR</dispatcher>
	</filter-mapping>
```

依赖spring-data-redis 只需要提供jedisConnectionFactory ，所以无论是redis集群模式、哨兵、单机都可以

srping-context-session.xml 配置
```<bean id="redisHttpSessionConfiguration"
		class="org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration">
		<property name="maxInactiveIntervalInSeconds" value="${redis.session.timeout}" />
		<property name="redisNamespace" value="${redis.session.prefix}"/>
		<property name="defaultRedisSerializer" ref="jdkSerializer"></property>
		<property name="cookieSerializer" ref="defaultCookieSerializer"></property>
	</bean>
	<bean id="jdkSerializer"
        class="org.springframework.data.redis.serializer.JdkSerializationRedisSerializer" />
        <!-- 都可以使用默认配置 -->
     <bean id="defaultCookieSerializer" class="org.springframework.session.web.http.DefaultCookieSerializer">
     	<property name="cookiePath" value="${cookie.path}"></property>
     	<property name="domainName" value="${cookie.domainName}"></property>
     	<property name="cookieName" value="${cookie.cookieName}"></property>
     </bean>
```