package com.epam.hw1.model;

import com.google.common.base.Objects;

/**
 * Created by Yevhen_Larikov on 20.12.2015.
 */
public class NoteBean {
    private String note;
    private String author;

    public NoteBean() {
    }

    public NoteBean(String note, String author) {
        this.note = note;
        this.author = author;
    }

    public String getNote() {
        return note;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteBean noteBean = (NoteBean) o;
        return Objects.equal(note, noteBean.note) &&
                Objects.equal(author, noteBean.author);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(note, author);
    }

    @Override
    public String toString() {
        return "NoteBean{" +
                "note='" + note + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
