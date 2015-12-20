package com.epam.hw1.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yevhn on 20.12.2015.
 */
public class User {
    private String name;
    private String username;
    private LocalDate dateOfBirth;
    private Timeline timeline = new Timeline();
    private List<User> friends = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public List<User> getFriends() {
        return new ArrayList<User>(friends);
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }
}
