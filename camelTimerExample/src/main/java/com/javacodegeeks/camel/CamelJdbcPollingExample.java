package com.javacodegeeks.camel;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelJdbcPollingExample {
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("dataSourceApplicationContext.xml");
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		JndiContext jndiContext = new JndiContext();
		jndiContext.bind("dataSource", dataSource);
		CamelContext camelContext = new DefaultCamelContext(jndiContext);
		try {
			camelContext.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from("timer://pollTable?period=1s")
                    .setBody(constant("select * from customer where status = 'NEW'"))
                    .to("jdbc:dataSource")
                    .split(simple("${body}"))
                    .process(new Processor() {
						
						public void process(Exchange exchange) throws Exception {
							Map<String, Object> customer = exchange.getIn().getBody(Map.class);
							System.out.println("Process customer " + customer);
						}
					});
				}
			});
			camelContext.start();
			Thread.sleep(3000);
		} finally {
			camelContext.stop();
			context.close();
		}
	}
}
