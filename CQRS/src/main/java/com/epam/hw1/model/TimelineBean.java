package com.epam.hw1.model;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents timeline entity.
 *
 * @author Yevhen_Larikov on 20.12.2015.
 */
public class TimelineBean {
    private List<NoteBean> timeline = new ArrayList<>();

    public List<NoteBean> getTimeline() {
        return new ArrayList<NoteBean>(timeline);
    }

    public void addNote(NoteBean noteBean) {
        this.timeline.add(noteBean);
    }

    @Override
    public String toString() {
        return "TimelineBean{" +
                "timeline=" + timeline +
                '}';
    }
}
