package com.epam.hw1.repository.impl;

import com.epam.hw1.exception.UsersAreNotFriendsException;
import com.epam.hw1.repository.FriendRepository;
import com.epam.hw1.repository.aggregate.FriendsAggregate;
import com.epam.hw1.repository.event.AddFriendEvent;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link FriendRepository} implementation.
 *
 * @author Yevhen_Larikov on 21.12.2015.
 */
@Repository
public class FriendRepositoryImpl implements FriendRepository {
    private Map<String, List<AddFriendEvent>> addFriendEvents = new HashMap<>();

    @Override
    public List<String> getFriends(String username) {
        FriendsAggregate friendsAggregate = new FriendsAggregate();
        addFriendEvents.get(username).forEach(friendsAggregate::onAddFriendEvent);
        return friendsAggregate.getFriends();
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
        return addFriendEvents.get(username) != null
                && addFriendEvents.get(username).stream()
                    .map(AddFriendEvent::getFriendUsername)
                    .anyMatch(friendUsername::equals);
    }

    @Override
    public void addFriend(long version, String username, String friendUsername) {
        if (addFriendEvents.get(username) == null) {
            addFriendEvents.put(username, new ArrayList<>());
        }
        addFriendEvents.get(username).add(new AddFriendEvent(version, friendUsername));
    }
}
