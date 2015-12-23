package com.epam.hw1.service;

import com.epam.hw1.exception.UserNotFoundException;

import java.util.List;

/**
 * Created by Yevhn on 22.12.2015.
 */
public interface FriendService {
    void makeFriends(String username, String friendUsername) throws UserNotFoundException;

    List<String> getFriends(String username) throws UserNotFoundException;

    boolean isFriends(String username, String friendUsername) throws UserNotFoundException;

    void addFriend(String username, String friendUsername) throws UserNotFoundException;
}
