package com.epam.hw1.repository;

import com.epam.hw1.exception.UsersAreNotFriendsException;

import java.util.List;

/**
 * Created by Yevhn on 22.12.2015.
 */
public interface FriendRepository {
    void makeFriends(String username, String friendUsername);

    List<String> getFriends(String username);

    void checkIsFriend(String username, String friendUsername) throws UsersAreNotFriendsException;

    void addFriend(String username, String friendUsername);

    boolean isFriends(String username, String friendUsername);
}
