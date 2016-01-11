package com.epam.hw3.service;

import com.epam.hw3.model.Expression;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author Yevhen_Larikov
 */
@Service
public class ExpressionGenerator implements Processor {
    public static final int MAX_LIMIT = 10;

    public void process(Exchange exchange) throws Exception {
        Expression expression = generateRandomExpression();
        Message message = exchange.getOut();
        message.setHeader("operation", expression.getOperation().name());
        message.setBody(expression);
    }

    private Expression generateRandomExpression() {
        Expression expression = new Expression();
        Random random = new Random();
        expression.setFirstArgument(random.nextInt(MAX_LIMIT) + 1);
        expression.setSecondArgument(random.nextInt(MAX_LIMIT) + 1);
        Expression.Operation[] operations = Expression.Operation.values();
        expression.setOperation(operations[random.nextInt(operations.length)]);
        return expression;
    }
}
