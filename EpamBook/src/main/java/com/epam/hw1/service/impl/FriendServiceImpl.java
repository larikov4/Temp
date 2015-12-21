package com.epam.hw1.service.impl;

import com.epam.hw1.repository.FriendRepository;
import com.epam.hw1.repository.TimelineRepository;
import com.epam.hw1.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yevhen_Larikov on 21.12.2015.
 */
@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Override
    public void makeFriends(String username, String friendUsername) {
        friendRepository.makeFriends(username, friendUsername);
    }

    @Override
    public List<String> getFriends(String username) {
        return friendRepository.getFriends(username);
    }

    @Override
    public boolean isFriends(String username, String friendUsername) {
        return friendRepository.isFriends(username, friendUsername);
    }

    @Override
    public void addFriend(String username, String friendUsername) {
        friendRepository.addFriend(username, friendUsername);
    }
}
