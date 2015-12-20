package com.epam.hw1.model.impl;

import com.epam.hw1.model.Ticket;
import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <code>Ticket</code> implementation.
 *
 * @author Yevhen_Larikov
 */
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketBean implements Ticket{
    @XmlAttribute(name = "id")
    private long id;
    @XmlAttribute(name = "event")
    private long eventId;
    @XmlAttribute(name = "user")
    private long userId;
    @XmlAttribute(name = "category")
    private Ticket.Category category;
    @XmlAttribute(name = "place")
    private int place;

    public TicketBean() {
    }

    public TicketBean(long id) {
        this.id = id;
    }

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
