package com.epam.hw1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yevhn on 20.12.2015.
 */
public class Timeline {
    private List<Note> notes;

    public List<Note> getNotes() {
        return new ArrayList<Note>(notes);
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }
}
