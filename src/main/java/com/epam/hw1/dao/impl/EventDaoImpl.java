package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.EventDao;
import com.epam.hw1.model.Event;
import com.epam.hw1.storage.Storage;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * <code>EventDao</code> implementation.
 *
 * @author Yevhen_Larikov
 */
@Repository
public class EventDaoImpl implements EventDao {
    private static final Logger LOG = Logger.getLogger(EventDaoImpl.class);
    public static final String EVENT_PREFIX = "event";
    private Storage storage;

    /**
     * Injects storage into Dao.
     *
     * @param storage Storage
     */
    @Autowired
    public void setStorage(@Qualifier("storage")Storage storage) {
        this.storage = storage;
    }

    @Override
    public Event getEventById(long id) {
        return storage.get(EVENT_PREFIX + id);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        if (title == null || pageNum < 1 || pageSize < 1) {
            LOG.warn("Some of parameters were invalid. title:" + title +
                    ", pageNum:" + pageNum + ", pageSize:" + pageSize);
            return Collections.emptyList();
        }
        List<Event> events = storage.getAll().entrySet().stream()
                .filter(entry -> entry.getKey().contains(EVENT_PREFIX))
                .map(entry -> (Event) entry.getValue())
                .filter(event -> event.getTitle() != null && title.contains(event.getTitle()))
                .collect(toList());

        List<List<Event>> pages = Lists.partition(events, pageSize);
        return pages.size() >= pageNum ? pages.get(pageNum - 1) : Collections.emptyList();
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        if (day == null || pageNum < 1 || pageSize < 1) {
            LOG.warn("Some of parameters were invalid. day:" + day +
                    ", pageNum:" + pageNum + ", pageSize:" + pageSize);
            return Collections.emptyList();
        }
        List<List<Event>> pages = Lists.partition(getAllEventsOfDay(day), pageSize);
        return pages.size() >= pageNum ? pages.get(pageNum - 1) : Collections.emptyList();
    }

    private List<Event> getAllEventsOfDay(Date day) {
        if (day == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        List<Event> events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(day);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);

        for (Map.Entry<String, Object> entry : storage.getAll().entrySet()) {
            if(entry.getKey().contains(EVENT_PREFIX)) {
                Event event = (Event) entry.getValue();
                calendar.setTime(day);
                if (dayOfYear == calendar.get(Calendar.DAY_OF_YEAR) && year == calendar.get(Calendar.YEAR)) {
                    events.add(event);
                }
            }
        }
        return events;
    }

    @Override
    public Event createEvent(Event event) {
        if (event == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        return storage.put(EVENT_PREFIX + event.getId(), event);
    }

    @Override
    public Event updateEvent(Event event) {
        if (event == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        return storage.put(EVENT_PREFIX + event.getId(), event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return storage.remove(EVENT_PREFIX + eventId);
    }
}
