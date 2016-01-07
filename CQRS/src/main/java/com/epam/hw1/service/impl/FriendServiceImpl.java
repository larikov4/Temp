package com.epam.hw1.service.impl;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.repository.FriendRepository;
import com.epam.hw1.repository.UserRepository;
import com.epam.hw1.service.FriendService;
import com.epam.hw1.service.version.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link FriendService} implementation.
 *
 * @author Yevhen_Larikov on 21.12.2015.
 */
@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VersionService versionService;

    @Override
    public void makeFriends(String username, String friendUsername) throws UserNotFoundException {
        addFriend(username, friendUsername);
        addFriend(friendUsername, username);
    }

    @Override
    public List<String> getFriends(String username) throws UserNotFoundException {
        userRepository.checkUserExistence(username);
        return friendRepository.getFriends(username);
    }

    @Override
    public boolean isFriends(String username, String friendUsername) throws UserNotFoundException {
        userRepository.checkUserExistence(username);
        userRepository.checkUserExistence(friendUsername);
        return friendRepository.isFriends(username, friendUsername);
    }

    private void addFriend(String username, String friendUsername) throws UserNotFoundException {
        long actualVersion;
        do{
            actualVersion = versionService.getActualVersion();
            userRepository.checkUserExistence(username);
            userRepository.checkUserExistence(friendUsername);
        } while(!versionService.compareAndSwapActualVersion(actualVersion));
        friendRepository.addFriend(actualVersion, username, friendUsername);
    }
}
