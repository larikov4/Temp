package com.epam.hw3.service.processor;

import com.epam.hw3.model.Expression;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

/**
 * @author Yevhen_Larikov
 */
@Service
public class SubOperationProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        Expression expression = (Expression) exchange.getIn().getBody();
        int result = expression.getFirstArgument() - expression.getSecondArgument();
        exchange.getOut().setBody(result);
    }
}