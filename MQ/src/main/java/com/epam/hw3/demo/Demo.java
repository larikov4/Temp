package com.epam.hw3.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {
	public static void main(String[] args) throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		CamelContext camelContext = SpringCamelContext.springCamelContext(
				appContext, false);
		camelContext.start();
		Thread.sleep(3000);
	}
}
