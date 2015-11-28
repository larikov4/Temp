package com.epam.hw1.model.impl;

import com.epam.hw1.model.Ticket;
import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketBean that = (TicketBean) o;
        return id == that.id &&
                eventId == that.eventId &&
                userId == that.userId &&
                place == that.place &&
                category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, eventId, userId, category, place);
    }

    @Override
    public String toString() {
        return "TicketBean{" +
                "id=" + id +
                ", userId=" + userId +
                ", eventId=" + eventId +
                ", category=" + category +
                ", place=" + place +
                '}';
    }
}
