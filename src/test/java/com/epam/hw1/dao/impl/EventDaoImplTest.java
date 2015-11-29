package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.EventDao;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.impl.EventBean;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Yevhen_Larikov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-config.xml")
@Transactional
public class EventDaoImplTest {
    private static final int EXISTING_EVENT_ID = 1;
    private static final int NEW_EVENT_ID = 20;
    private static final String EXISTING_EVENT_TITLE = "title1";
    private static final String NEW_EVENT_TITLE = "title20";
    private static final int EXISTING_EVENT_PRICE = 10;
    private static final int NEW_EVENT_PRICE = 20;
    private static final int PAGE_SIZE = 2;
    private static final int PAGE_NUM = 1;

    private static Date existingEventDate;
    private static Date newEventDate;

    @Autowired
    private EventDao eventDao;

    private Event existingEvent;
    private Event newEvent;

    @BeforeClass
    public static void setUpDates() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        existingEventDate = simpleDateFormat.parse("2015-11-24");
        newEventDate = simpleDateFormat.parse("2025-11-24");
    }

    @Before
    public void setUp() {
        existingEvent = new EventBean();
        existingEvent.setId(EXISTING_EVENT_ID);
        existingEvent.setTitle(EXISTING_EVENT_TITLE);
        existingEvent.setDate(existingEventDate);
        existingEvent.setPrice(EXISTING_EVENT_PRICE);

        newEvent = new EventBean();
        newEvent.setId(NEW_EVENT_ID);
        newEvent.setTitle(NEW_EVENT_TITLE);
        newEvent.setDate(newEventDate);
        newEvent.setPrice(NEW_EVENT_PRICE);
    }

    @Test
    public void shouldReturnEventById(){
        assertEquals(existingEvent, eventDao.getEventById(EXISTING_EVENT_ID));
    }

    @Test
    public void shouldReturnListWhenEventsByTitleIsEnough(){
        List<Event> eventsByTitle = eventDao.getEventsByTitle(EXISTING_EVENT_TITLE, PAGE_SIZE, PAGE_NUM);
        assertEquals(PAGE_SIZE, eventsByTitle.size());
        assertEquals(existingEvent, eventsByTitle.get(0));
    }

    @Test
    public void shouldReturnEmptyListWhenEventsByTitleIsEnough(){
        List<Event> eventsByTitle = eventDao.getEventsByTitle(NEW_EVENT_TITLE, PAGE_SIZE, PAGE_NUM);
        assertTrue(eventsByTitle.isEmpty());
    }

    @Test
    public void shouldReturnListWhenEventsByDateIsEnough(){
        List<Event> eventsForDate = eventDao.getEventsForDay(existingEventDate, PAGE_SIZE, PAGE_NUM);
        assertEquals(PAGE_SIZE, eventsForDate.size());
        assertEquals(existingEvent, eventsForDate.get(0));
    }

    @Test
    public void shouldReturnEmptyListWhenEventsByDateIsEnough(){
        List<Event> eventsForDate = eventDao.getEventsForDay(newEventDate, PAGE_SIZE, PAGE_NUM);
        assertTrue(eventsForDate.isEmpty());
    }

    @Test
    public void shouldCreateEvent(){
        assertEquals(newEvent, eventDao.createEvent(newEvent));
        assertNotNull(newEvent.getId());
    }

    @Test
    public void shouldUpdateEvent(){
        newEvent.setId(EXISTING_EVENT_ID);
        assertEquals(newEvent, eventDao.updateEvent(newEvent));
        assertEquals(newEvent, eventDao.getEventById(EXISTING_EVENT_ID));
    }

    @Test
    public void shouldRemoveEventById(){
        assertTrue(eventDao.deleteEvent(EXISTING_EVENT_ID));
        assertNull(eventDao.getEventById(EXISTING_EVENT_ID));
    }

    @Test
    public void shouldReturnFalseWhenRemovingNotExistingEvent(){
        assertFalse(eventDao.deleteEvent(NEW_EVENT_ID));
    }
}
