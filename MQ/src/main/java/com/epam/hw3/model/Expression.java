package com.epam.hw3.model;

/**
 * @author Yevhen_Larikov
 */
public class Expression {
    private int firstArgument;
    private int secondArgument;
    private Operation operation;

    public int getFirstArgument() {
        return firstArgument;
    }

    public void setFirstArgument(int firstArgument) {
        this.firstArgument = firstArgument;
    }

    public int getSecondArgument() {
        return secondArgument;
    }

    public void setSecondArgument(int secondArgument) {
        this.secondArgument = secondArgument;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public enum Operation{
        ADD, SUB, MUL, DIV;
    }
}
