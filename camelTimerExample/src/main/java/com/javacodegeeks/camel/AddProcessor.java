package com.javacodegeeks.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

/**
 * Created by Yevhn on 11.01.2016.
 */
public class AddProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        exchange.getOut().setBody(exchange.getIn().getBody().toString() + "=2\n");
    }
}