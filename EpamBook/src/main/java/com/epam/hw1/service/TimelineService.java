package com.epam.hw1.service;

import com.epam.hw1.exception.EpambookException;
import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.exception.UsersAreNotFriendsException;
import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;

/**
 * Created by Yevhn on 20.12.2015.
 */
public interface TimelineService {
    TimelineBean getTimelineBean(String username) throws UserNotFoundException;

    TimelineBean getFriendTimelineBean(String username, String friendUsername) throws EpambookException;

    void addNote(String username, NoteBean noteBean) throws UserNotFoundException;
}
