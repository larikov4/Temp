package com.epam.hw1.model.impl;

import com.epam.hw1.model.Ticket;

/**
 * <code>Ticket</code> implementation.
 *
 * @author Yevhen_Larikov
 */
public class TicketBean implements Ticket{
    private long id;
    private long eventId;
    private long userId;
    private Ticket.Category category;
    private int place;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int getPlace() {
        return place;
    }

    @Override
    public void setPlace(int place) {
        this.place = place;
    }
}
