package com.epam.hw1.exception;

/**
 * Exception should be thrown when user wasn't found.
 *
 * Created by Yevhn on 23.12.2015.
 */
public class UserNotFoundException extends EpambookException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
