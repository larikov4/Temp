package com.epam.hw1.service;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.exception.UsersAreNotFriendsException;
import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;
import org.springframework.stereotype.Service;

/**
 * Service for manipulation timeline entities.
 *
 * Created by Yevhn on 20.12.2015.
 */
public interface TimelineService {
    /**
     * Returns a timeline of passed user.
     * @param username the username timeline owner
     * @return the timeline bean
     * @throws UserNotFoundException is thrown when user wasn't found.
     */
    TimelineBean getTimelineBean(String username) throws UserNotFoundException;

    /**
     * Returns a timeline of passed user's friend.
     * @param username the username
     * @param friendUsername the friend username
     * @return the timeline bean
     * @throws UserNotFoundException is thrown when user wasn't found.
     * @throws UsersAreNotFriendsException is thrown when users aren't friends.
     */
    TimelineBean getFriendTimelineBean(String username, String friendUsername)
            throws UserNotFoundException, UsersAreNotFriendsException;

    /**
     * Adds note to passed user's timeline.
     * @param username the username
     * @param noteBean the note with author
     * @throws UserNotFoundException is thrown when user wasn't found.
     */
    void addNote(String username, NoteBean noteBean) throws UserNotFoundException;
}


