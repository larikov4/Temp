package com.epam.hw1.repository.aggregate;

import com.epam.hw1.model.TimelineBean;
import com.epam.hw1.repository.event.AddNoteEvent;

/**
 * Aggregate root for timelines. Should be used to apply events on it.
 *
 * @author Yevhen_Larikov on 06.01.2016.
 */
public class TimelineAggregate {
    private TimelineBean timeline = new TimelineBean();

    public TimelineBean getTimeline() {
        return timeline;
    }

    /**
     * Applies passed event on itself.
     *
     * @param event event to apply on aggregate
     */
    public void onAddNoteEvent(AddNoteEvent event){
        timeline.addNote(event.getNote());
    }
}
