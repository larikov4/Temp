package com.javacodegeeks.camel;

import java.util.Random;

/**
 * Created by Yevhn on 11.01.2016.
 */
public class MyBean {
    public int help() {
        return new Random().nextBoolean() ? 1 : -1;
    }
}
