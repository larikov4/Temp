package com.epam.hw1.model.impl;

import com.epam.hw1.model.Event;
import com.google.common.base.Objects;

import java.util.Date;

/**
 * <code>Event</code> implementation.
 *
 * @author Yevhen_Larikov
 */
public class EventBean implements Event {
    private long id;
    private String title;
    private Date date;
    private double price;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventBean eventBean = (EventBean) o;
        return id == eventBean.id &&
                Double.compare(eventBean.price, price) == 0 &&
                Objects.equal(title, eventBean.title) &&
                Objects.equal(date, eventBean.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, date, price);
    }

    @Override
    public String toString() {
        return "EventBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
