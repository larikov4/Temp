package com.epam.hw1.service.impl;

import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;
import com.epam.hw1.repository.TimelineRepository;
import com.epam.hw1.repository.UserRepository;
import com.epam.hw1.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yevhen_Larikov on 20.12.2015.
 */
@Repository
public class TimelineServiceImpl implements TimelineService {
    @Autowired
    private TimelineRepository timelineRepository;

    @Override
    public TimelineBean getTimelineBean(String username) {
        return timelineRepository.getTimelineBean(username);
    }

    @Override
    public void addNote(String username, NoteBean noteBean) {
        timelineRepository.addNote(username, noteBean);
    }
}
