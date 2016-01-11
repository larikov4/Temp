package com.epam.hw3.service.processor;

import com.epam.hw3.model.Expression;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

/**
 * {@link OperationProcessor} implementation for
 * {@link com.epam.hw3.model.Expression.Operation#DIV} operation.
 *
 * @author Yevhen_Larikov
 */
@Service
public class DivOperationProcessor extends OperationProcessor {
    @Override
    public int executeOperation(int firstValue, int secondValue) {
        return firstValue / secondValue;
    }
}
