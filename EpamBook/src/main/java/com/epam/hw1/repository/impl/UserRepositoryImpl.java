package com.epam.hw1.repository.impl;

import com.epam.hw1.model.Note;
import com.epam.hw1.model.Timeline;
import com.epam.hw1.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yevhn on 20.12.2015.
 */
@Repository
public class UserRepositoryImpl {
    private Map<String, User> users = new HashMap<>();

    public void addUser(User user){
        users.put(user.getUsername(), user);
    }

    public void getUser(String username){
        users.get(username);
    }

    public Timeline getTimeline(String username){
        return users.get(username).getTimeline();
    }

    public void addNote(String username, Note note){
        users.get(username).getTimeline().addNote(note);
    }

    public void makeFriends(String username, String friendUsername){
        User user = users.get(username);
        User friend = users.get(friendUsername);
        user.addFriend(friend);
        friend.addFriend(user);
    }

    public List<User> getFriends(String username){
        return users.get(username).getFriends();
    }

    public boolean isFrends(String username, String friendUsername) {
        return users.get(username).getFriends().contains(users.get(friendUsername));
    }
}
