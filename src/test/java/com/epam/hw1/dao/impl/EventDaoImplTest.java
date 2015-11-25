package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.EventDao;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.impl.EventBean;
import com.epam.hw1.storage.Storage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author Yevhen_Larikov
 */
@RunWith(MockitoJUnitRunner.class)
public class EventDaoImplTest {
    private static final long EVENT_ID = 1L;
    private static final String EVENT_ID_WITH_PREFIX = EventDaoImpl.EVENT_PREFIX + EVENT_ID;
    private static final String EVENT_TITLE = "testTitle";
    private static final String ANOTHER_EVENT_TITLE = "testTitle1";
    private static final Date EVENT_DATE = new Date();
    private static final int PAGE_SIZE = 2;
    private static final int PAGE_NUM = 1;

    @Mock
    private Storage storage;

    @Mock
    private Event event;

    @InjectMocks
    private EventDao eventDao = new EventDaoImpl();

    @Test
    public void shouldReturnFullListWhenEventsWithTitleIsEnough() {
        Map<String, Object> events = generateEvents(PAGE_SIZE * PAGE_NUM);
        when(storage.getAll()).thenReturn(spy(events));

        assertEquals(PAGE_SIZE, eventDao.getEventsByTitle(EVENT_TITLE, PAGE_SIZE, PAGE_NUM).size());
    }

    @Test
    public void shouldReturnNotFullListWhenEventsWithTitleIsNotEnough() {
        Map<String, Object> events = generateEvents(PAGE_SIZE * PAGE_NUM - PAGE_SIZE / 2);
        when(storage.getAll()).thenReturn(spy(events));

        assertEquals(PAGE_SIZE / 2, eventDao.getEventsByTitle(EVENT_TITLE, PAGE_SIZE, PAGE_NUM).size());
    }

    @Test
    public void shouldReturnEmptyListWhenNoEventsWithTitleWereFound() {
        Map<String, Object> events = new HashMap<>();
        when(storage.getAll()).thenReturn(spy(events));

        assertEquals(Collections.emptyList(), eventDao.getEventsByTitle(EVENT_TITLE, PAGE_SIZE, PAGE_NUM));
    }

    @Test
    public void shouldReturnFullListWhenEventsForDayIsEnough() {
        Map<String, Object> events = generateEvents(PAGE_SIZE * PAGE_NUM);
        when(storage.getAll()).thenReturn(spy(events));

        assertEquals(PAGE_SIZE, eventDao.getEventsByTitle(EVENT_TITLE, PAGE_SIZE, PAGE_NUM).size());
    }

    @Test
    public void shouldReturnNotFullListWhenEventsForDayIsNotEnough() {
        Map<String, Object> events = generateEvents(PAGE_SIZE * PAGE_NUM - PAGE_SIZE / 2);
        when(storage.getAll()).thenReturn(spy(events));

        assertEquals(PAGE_SIZE / 2, eventDao.getEventsByTitle(EVENT_TITLE, PAGE_SIZE, PAGE_NUM).size());
    }

    @Test
    public void shouldReturnEmptyListWhenNoEventsForDayWereFound() {
        Map<String, Object> events = new HashMap<>();
        when(storage.getAll()).thenReturn(spy(events));

        assertEquals(Collections.emptyList(), eventDao.getEventsByTitle(EVENT_TITLE, PAGE_SIZE, PAGE_NUM));
    }

    @Test
    public void shouldPutEventIntoStorage() {
        when(storage.put(EVENT_ID_WITH_PREFIX, event)).thenReturn(event);
        when(event.getId()).thenReturn(EVENT_ID);

        assertEquals(event, eventDao.createEvent(event));
        Mockito.verify(storage).put(EVENT_ID_WITH_PREFIX, event);
    }

    @Test
    public void shouldUpdateEventInStorage() {
        when(storage.put(EVENT_ID_WITH_PREFIX, event)).thenReturn(event);
        when(event.getId()).thenReturn(EVENT_ID);

        assertEquals(event, eventDao.updateEvent(event));
        Mockito.verify(storage).put(EVENT_ID_WITH_PREFIX, event);
    }

    @Test
    public void shouldReturnEventById() {
        when(storage.get(EVENT_ID_WITH_PREFIX)).thenReturn(event);

        assertEquals(event, eventDao.getEventById(EVENT_ID));
        Mockito.verify(storage).get(EVENT_ID_WITH_PREFIX);
    }

    @Test
    public void shouldRemoveEventFromStorage() {
        when(storage.remove(EVENT_ID_WITH_PREFIX)).thenReturn(true);

        eventDao.deleteEvent(EVENT_ID);
        Mockito.verify(storage).remove(EVENT_ID_WITH_PREFIX);
    }

    private Map<String, Object> generateEvents(int amount) {
        Map<String, Object> events = new HashMap<>();
        for (int i = 0; i < amount; i++) {
            events.put(EventDaoImpl.EVENT_PREFIX + i, generateEvent(i, EVENT_TITLE, EVENT_DATE));
        }
        addInvalidEvents(events);
        return events;
    }

    private Map<String, Object> addInvalidEvents(Map<String, Object> events) {
        int k = 0;
        events.put(EventDaoImpl.EVENT_PREFIX + --k, generateEvent(k, null, EVENT_DATE));
        events.put(EventDaoImpl.EVENT_PREFIX + --k, generateEvent(k, ANOTHER_EVENT_TITLE, null));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        events.put(EventDaoImpl.EVENT_PREFIX + --k, generateEvent(k, ANOTHER_EVENT_TITLE, calendar.getTime()));
        return events;
    }

    private Event generateEvent(int number, String title, Date date) {
        Event event = new EventBean();
        event.setId(number);
        event.setTitle(title);
        event.setDate(EVENT_DATE);
        return event;
    }
}
