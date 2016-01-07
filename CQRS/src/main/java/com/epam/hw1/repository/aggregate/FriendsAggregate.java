package com.epam.hw1.repository.aggregate;

import com.epam.hw1.repository.event.AddFriendEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate root for friends. Should be used to apply events on it.
 *
 * @author Yevhn on 07.01.2016.
 */
public class FriendsAggregate {
    private List<String> friends = new ArrayList<>();

    public List<String> getFriends() {
        return new ArrayList<>(friends);
    }

    /**
     * Applies passed event on itself.
     *
     * @param event event to apply on aggregate
     */
    public void onAddFriendEvent(AddFriendEvent event){
        friends.add(event.getFriendUsername());
    }
}
