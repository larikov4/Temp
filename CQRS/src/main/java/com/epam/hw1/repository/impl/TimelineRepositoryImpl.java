package com.epam.hw1.repository.impl;

import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;
import com.epam.hw1.repository.TimelineRepository;
import com.epam.hw1.repository.aggregate.TimelineAggregate;
import com.epam.hw1.repository.event.AddNoteEvent;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link TimelineRepository} implementation.
 *
 * @author Yevhen_Larikov on 20.12.2015.
 */
@Repository
public class TimelineRepositoryImpl implements TimelineRepository{
    private Map<String, List<AddNoteEvent>> addNoteEvents = new HashMap<>();

    @Override
    public TimelineBean getTimelineBean(String username) {
        TimelineAggregate timelineAggregate = new TimelineAggregate();
        addNoteEvents.get(username).forEach(timelineAggregate::onAddNoteEvent);
        return timelineAggregate.getTimeline();
    }

    @Override
    public void addNote(long version, String username, NoteBean noteBean) {
        if (addNoteEvents.get(username) == null) {
            addNoteEvents.put(username, new ArrayList<>());
        }
        addNoteEvents.get(username).add(new AddNoteEvent(version, noteBean));
    }
}
