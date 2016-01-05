package com.epam.hw1.repository.impl;

import com.epam.hw1.exception.UsersAreNotFriendsException;
import com.epam.hw1.repository.FriendRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link FriendRepository} implementation.
 *
 * Created by Yevhen_Larikov on 21.12.2015.
 */
@Repository
public class FriendRepositoryImpl implements FriendRepository {
    private Map<String, List<String>> friends = new HashMap<>();

    @Override
    public void makeFriends(String username, String friendUsername) {
        addFriend(username, friendUsername);
        addFriend(friendUsername, username);
    }

    @Override
    public List<String> getFriends(String username) {
        return friends.get(username);
    }

    @Override
    public void checkIsFriend(String username, String friendUsername) throws UsersAreNotFriendsException {
        if (!isFriends(username, friendUsername)) {
            throw new UsersAreNotFriendsException("Users are not friends. Usernames: "
                    + username + " and " + friendUsername);
        }
    }

    @Override
    public boolean isFriends(String username, String friendUsername) {
        return friends.get(username) != null && friends.get(username).contains(friendUsername);
    }

    private void addFriend(String username, String friendUsername) {
        if (friends.get(username) == null) {
            friends.put(username, new ArrayList<>());
        }
        friends.get(username).add(friendUsername);
    }
}
