package com.epam.hw1.repository;

import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;

/**
 * Created by Yevhn on 20.12.2015.
 */
public interface TimelineRepository {
    TimelineBean getTimelineBean(String username);

    void addNote(String username, NoteBean noteBean);
}