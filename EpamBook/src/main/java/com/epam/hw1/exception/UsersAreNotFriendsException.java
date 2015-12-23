package com.epam.hw1.exception;

/**
 * Created by Yevhn on 23.12.2015.
 */
public class UsersAreNotFriendsException extends EpambookException {

    public UsersAreNotFriendsException() {
    }

    public UsersAreNotFriendsException(String message) {
        super(message);
    }
}
