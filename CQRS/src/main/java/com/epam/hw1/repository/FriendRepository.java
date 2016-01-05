package com.epam.hw1.repository;

import com.epam.hw1.exception.UsersAreNotFriendsException;

import java.util.List;

/**
 * Repository for storing friends.
 *
 * Created by Yevhn on 22.12.2015.
 */
public interface FriendRepository {

    /**
     * Makes two users friends. Friend is symmetric relation.
     * @param username the username
     * @param friendUsername the friend username
     */
    void makeFriends(String username, String friendUsername);

    /**
     * Returns all friend of user by passed username.
     * @param username the username
     * @return list of friends
     */
    List<String> getFriends(String username);

    /**
     * Checks are users friends and throws exception when users are not friends.
     * @param username the username
     * @param friendUsername the friend username
     * @throws UsersAreNotFriendsException is thrown when users are not friends.
     */
    void checkIsFriend(String username, String friendUsername) throws UsersAreNotFriendsException;

    /**
     * Returns boolean value whether passed users are friends.
     * @param username the username
     * @param friendUsername the friend username
     * @return boolean whether users are friends.
     */
    boolean isFriends(String username, String friendUsername);
}
