package com.epam.hw3.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Demo class.
 *
 * @author Yevhen_Larikov
 */
public class Demo {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
