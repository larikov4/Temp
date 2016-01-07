package com.epam.hw1.service.impl;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.exception.UsersAreNotFriendsException;
import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;
import com.epam.hw1.repository.FriendRepository;
import com.epam.hw1.repository.TimelineRepository;
import com.epam.hw1.repository.UserRepository;
import com.epam.hw1.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link TimelineService} implementation.
 *
 * Created by Yevhen_Larikov on 20.12.2015.
 */
@Service
public class TimelineServiceImpl implements TimelineService {
    @Autowired
    private TimelineRepository timelineRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepository friendRepository;

    @Override
    public TimelineBean getTimelineBean(String username) throws UserNotFoundException {
        userRepository.checkUserExistence(username);
        return timelineRepository.getTimelineBean(username);
    }

    @Override
    public TimelineBean getFriendTimelineBean(String username, String friendUsername)
            throws UserNotFoundException, UsersAreNotFriendsException {
        userRepository.checkUserExistence(username);
        userRepository.checkUserExistence(friendUsername);
        friendRepository.checkIsFriend(username, friendUsername);
        return timelineRepository.getTimelineBean(friendUsername);
    }

    @Override
    public void addNote(String username, NoteBean noteBean) throws UserNotFoundException {
        userRepository.checkUserExistence(username);
        timelineRepository.addNote(username, noteBean);
    }

    @Override
    public void addNoteToFriendTimeline(String username, String friendUsername, NoteBean noteBean)
            throws UserNotFoundException, UsersAreNotFriendsException {
        userRepository.checkUserExistence(username);
        userRepository.checkUserExistence(friendUsername);
        friendRepository.checkIsFriend(username, friendUsername);
        timelineRepository.addNote(friendUsername, noteBean);
    }
}
