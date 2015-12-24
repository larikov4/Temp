package com.epam.hw1.repository.impl;

import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;
import com.epam.hw1.repository.TimelineRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link TimelineRepository} implementation.
 *
 * Created by Yevhen_Larikov on 20.12.2015.
 */
@Repository
public class TimelineRepositoryImpl implements TimelineRepository{
    private Map<String, TimelineBean> timelines = new HashMap<>();

    @Override
    public TimelineBean getTimelineBean(String username) {
        return timelines.get(username);
    }

    @Override
    public void addNote(String username, NoteBean noteBean) {
        if (timelines.get(username) == null) {
            timelines.put(username, new TimelineBean());
        }
        timelines.get(username).addNote(noteBean);
    }
}
