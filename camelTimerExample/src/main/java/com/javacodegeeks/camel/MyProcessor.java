package com.javacodegeeks.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

/**
 * Created by Yevhn on 11.01.2016.
 */
public class MyProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        Message out = exchange.getOut();
        out.setHeader("operation", "add");
        out.setBody("1+1");
    }
}