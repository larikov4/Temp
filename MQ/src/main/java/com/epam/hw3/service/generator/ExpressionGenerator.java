package com.epam.hw3.service.generator;

import com.epam.hw3.model.Expression;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Processor is designed to generate random expressions.
 *
 * @author Yevhen_Larikov
 */
@Service
public class ExpressionGenerator implements Processor {
    public static final int MAX_LIMIT = 10;

    /**
     * Generates new random expression put it into message body and put
     * operation into message header.
     *
     * @param exchange incoming exchange
     * @throws Exception the exception
     */
    public void process(Exchange exchange) throws Exception {
        Expression expression = generateRandomExpression();
        Message message = exchange.getOut();
        message.setHeader("operation", expression.getOperation().name());
        message.setBody(expression);
    }

    /**
     * Generates new random expression with two values between 1 and {@value #MAX_LIMIT} inclusively
     * and random {@link com.epam.hw3.model.Expression.Operation} value.
     *
     * @return random expression instance
     */
    private Expression generateRandomExpression() {
        Expression expression = new Expression();
        Random random = new Random();
        expression.setInt1(random.nextInt(MAX_LIMIT) + 1);
        expression.setInt2(random.nextInt(MAX_LIMIT) + 1);
        Expression.Operation[] operations = Expression.Operation.values();
        expression.setOperation(operations[random.nextInt(operations.length)]);
        return expression;
    }
}
