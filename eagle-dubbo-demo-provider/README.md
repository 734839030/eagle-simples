## eagle-dubbo provider的例子
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

2.整合内嵌jetty ，利用spring mvc 发布restful 服务（dubbox resteasy 很难用，性能也没有springmvc 好 ）。**测试** 阶段可以使用如下命令启动dubbo rest服务。

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

zipkin 调用链监控（不太好集成，rest 比较容易。）
rest 服务推荐用kong 做网关。

##后续完善功能
1.dubbo filter

2.dubbo rest 统一参数校验
