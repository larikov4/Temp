package com.epam.hw1.repository.aggregate;

import com.epam.hw1.model.TimelineBean;
import com.epam.hw1.repository.event.AddNoteEvent;

/**
 * Created by Yevhn on 06.01.2016.
 */
public class TimelineAggregate {
    private TimelineBean timeline = new TimelineBean();

    public TimelineBean getTimeline() {
        return timeline;
    }

    public void onAddNoteEvent(AddNoteEvent event){
        timeline.addNote(event.getNote());
    }
}
