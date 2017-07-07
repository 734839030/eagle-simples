package com.seezoon.eagle.job.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-context*.xml");
		System.out.println(System.getProperty("java.class.path"));
	}
}
