package com.epam.hw1.exception;

/**
 * Created by Yevhn on 23.12.2015.
 */
public class UserNotFoundException extends EpambookException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
