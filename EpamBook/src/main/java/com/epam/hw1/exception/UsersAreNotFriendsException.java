package com.epam.hw1.exception;

/**
 * Exception should be thrown when users suppose to be friends but they don't.
 *
 * Created by Yevhn on 23.12.2015.
 */
public class UsersAreNotFriendsException extends EpambookException {

    public UsersAreNotFriendsException(String message) {
        super(message);
    }
}
