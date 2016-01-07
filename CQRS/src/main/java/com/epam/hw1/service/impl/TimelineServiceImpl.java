package com.epam.hw1.service.impl;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.exception.UsersAreNotFriendsException;
import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;
import com.epam.hw1.repository.FriendRepository;
import com.epam.hw1.repository.TimelineRepository;
import com.epam.hw1.repository.UserRepository;
import com.epam.hw1.service.TimelineService;
import com.epam.hw1.service.version.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link TimelineService} implementation.
 *
 * @author Yevhen_Larikov on 20.12.2015.
 */
@Service
public class TimelineServiceImpl implements TimelineService {
    @Autowired
    private TimelineRepository timelineRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private VersionService versionService;

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
        long actualVersion;
        do{
            actualVersion = versionService.getActualVersion();
            userRepository.checkUserExistence(username);
        } while(!versionService.compareAndSwapActualVersion(actualVersion));
        timelineRepository.addNote(actualVersion, username, noteBean);
    }

    @Override
    public void addNoteToFriendTimeline(String username, String friendUsername, NoteBean noteBean)
            throws UserNotFoundException, UsersAreNotFriendsException {
        long actualVersion;
        do{
            actualVersion = versionService.getActualVersion();
            userRepository.checkUserExistence(username);
            userRepository.checkUserExistence(friendUsername);
            friendRepository.checkIsFriend(username, friendUsername);
        } while(!versionService.compareAndSwapActualVersion(actualVersion));
        timelineRepository.addNote(actualVersion, friendUsername, noteBean);
    }
}
