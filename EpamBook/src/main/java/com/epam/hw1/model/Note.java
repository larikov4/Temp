package com.epam.hw1.model;

/**
 * Created by Yevhn on 20.12.2015.
 */
public class Note {
    private String note;
    private String author;

    public Note(String note, String author) {
        this.note = note;
        this.author = author;
    }

    public String getNote() {
        return note;
    }

    public String getAuthor() {
        return author;
    }
}
