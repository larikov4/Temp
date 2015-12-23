package com.epam.hw1.service.impl;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.repository.FriendRepository;
import com.epam.hw1.repository.UserRepository;
import com.epam.hw1.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yevhen_Larikov on 21.12.2015.
 */
@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void makeFriends(String username, String friendUsername) throws UserNotFoundException {
        checkUserExistence(username);
        checkUserExistence(friendUsername);
        friendRepository.makeFriends(username, friendUsername);
    }

    @Override
    public List<String> getFriends(String username) throws UserNotFoundException {
        checkUserExistence(username);
        return friendRepository.getFriends(username);
    }

    @Override
    public boolean isFriends(String username, String friendUsername) throws UserNotFoundException {
        checkUserExistence(username);
        checkUserExistence(friendUsername);
        return friendRepository.isFriends(username, friendUsername);
    }

    @Override
    public void addFriend(String username, String friendUsername) throws UserNotFoundException {
        checkUserExistence(username);
        checkUserExistence(friendUsername);
        friendRepository.addFriend(username, friendUsername);
    }

    private void checkUserExistence(String username) throws UserNotFoundException {
        if(!userRepository.exists(username)){
            throw new UserNotFoundException("Cannot find user. Username: " + username );
        }
    }
}
