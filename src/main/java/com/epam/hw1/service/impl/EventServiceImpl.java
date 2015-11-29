package com.epam.hw1.service.impl;

import com.epam.hw1.dao.EventDao;
import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.User;
import com.epam.hw1.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <code>EventService</code> implementation.
 *
 * @author Yevhen_Larikov
 */
@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private EventDao eventDao;

    /**
     * Injects <code>EventDao</code> into Service.
     *
     * @param eventDao EventDao
     */
    @Autowired
    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Event getEventById(long id) {
        return eventDao.getEventById(id);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventDao.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventDao.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    @Transactional
    public Event createEvent(Event event) {
        return eventDao.createEvent(event);
    }

    @Override
    @Transactional
    public Event updateEvent(Event event) {
        return eventDao.updateEvent(event);
    }

    @Override
    @Transactional
    public boolean deleteEvent(long eventId) {
        return eventDao.deleteEvent(eventId);
    }
}
