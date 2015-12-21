package com.epam.hw1.service;

import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;

/**
 * Created by Yevhn on 20.12.2015.
 */
public interface TimelineService {
    TimelineBean getTimelineBean(String username);

    void addNote(String username, NoteBean noteBean);
}
