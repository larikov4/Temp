package com.epam.hw1.service;

import com.epam.hw1.exception.UserNotFoundException;

import java.util.List;

/**
 * Service for manipulation friend entities.
 *
 * Created by Yevhn on 22.12.2015.
 */
public interface FriendService {
    /**
     * Makes two users friends. Friend is symmetric relation.
     * @param username the username
     * @param friendUsername the friend username
     * @throws UserNotFoundException is thrown when user wasn't found.
     */
    void makeFriends(String username, String friendUsername) throws UserNotFoundException;

    /**
     * Returns all friend of user by passed username.
     * @param username the username
     * @return list of friends
     * @throws UserNotFoundException is thrown when user wasn't found.
     */
    List<String> getFriends(String username) throws UserNotFoundException;

    /**
     * Returns boolean value whether passed users are friends.
     * @param username the username
     * @param friendUsername the friend username
     * @return boolean whether users are friends.
     * @throws UserNotFoundException is thrown when user wasn't found.
     */
    boolean isFriends(String username, String friendUsername) throws UserNotFoundException;
}