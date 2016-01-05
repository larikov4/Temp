package com.epam.hw1.repository;

import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;

/**
 * Repository for storing users' timeline.
 *
 * Created by Yevhn on 20.12.2015.
 */
public interface TimelineRepository {
    /**
     * Returns a timeline of passed user.
     * @param username the username timeline owner
     * @return the timeline bean
     */
    TimelineBean getTimelineBean(String username);

    /**
     * Adds note to passed user's timeline.
     * @param username the username
     * @param noteBean the note with author
     */
    void addNote(String username, NoteBean noteBean);
}
