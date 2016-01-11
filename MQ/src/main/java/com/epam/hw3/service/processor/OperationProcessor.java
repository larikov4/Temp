package com.epam.hw3.service.processor;

import com.epam.hw3.model.Expression;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Abstract class for operation processor. Should be used
 * to process messages in queue.
 *
 * @author Yevhen_Larikov
 */
public abstract class OperationProcessor implements Processor {
    /**
     * Executes operation and put result into message body.
     *
     * @param exchange incoming exchange
     * @throws Exception the exception
     */
    public void process(Exchange exchange) throws Exception {
        Expression expression = (Expression) exchange.getIn().getBody();
        int result = executeOperation(expression.getInt1(), expression.getInt2());
        exchange.getOut().setBody(result);
    }

    /**
     * Executes operation on passed parameters.
     *
     * @param firstValue the first value
     * @param secondValue the second value
     * @return operation result
     */
    public abstract int executeOperation(int firstValue, int secondValue);
}
