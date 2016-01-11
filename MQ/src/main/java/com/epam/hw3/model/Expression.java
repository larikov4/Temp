package com.epam.hw3.model;

/**
 * Bean for expression.
 *
 * @author Yevhen_Larikov
 */
public class Expression {
    private int int1;
    private int int2;
    private Operation operation;

    public int getInt1() {
        return int1;
    }

    public void setInt1(int int1) {
        this.int1 = int1;
    }

    public int getInt2() {
        return int2;
    }

    public void setInt2(int int2) {
        this.int2 = int2;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "Expression{" + int1 + " " + operation + " " + int2 + "}";
    }

    public enum Operation {
        ADD, SUB, MUL, DIV
    }
}
