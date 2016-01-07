package com.epam.hw1.repository;

import com.epam.hw1.exception.UsersAreNotFriendsException;

import java.util.List;

/**
 * Repository for storing friends.
 *
 * @author Yevhen_Larikov on 22.12.2015.
 */
public interface FriendRepository {
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

    /**
     * Add friend to passed username friends list.
     * @param version the version
     * @param username the username
     * @param friendUsername the friend username
     */
    void addFriend(long version, String username, String friendUsername);
}
