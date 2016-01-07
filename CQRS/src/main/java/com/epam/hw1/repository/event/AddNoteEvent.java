package com.epam.hw1.repository.event;

import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;

/**
 * Event on note addition.
 *
 * @author Yevhen_Larikov on 06.01.2016.
 */
public class AddNoteEvent implements Event{
    private long versionId;
    private NoteBean note;

    public AddNoteEvent(long versionId, NoteBean note) {
        this.versionId = versionId;
        this.note = note;
    }

    public NoteBean getNote() {
        return note;
    }

    @Override
    public long getVersionId() {
        return versionId;
    }
}