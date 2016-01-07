package com.epam.hw1.exception;

/**
 * Exception should be thrown when user wasn't found.
 *
 * @author Yevhen_Larikov on 23.12.2015.
 */
public class UserNotFoundException extends EpambookException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
