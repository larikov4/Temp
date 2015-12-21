package com.epam.hw1.repository;

import java.util.List;

/**
 * Created by Yevhn on 22.12.2015.
 */
public interface FriendRepository {
    void makeFriends(String username, String friendUsername);

    List<String> getFriends(String username);

    boolean isFriends(String username, String friendUsername);

    void addFriend(String username, String friendUsername);
}
