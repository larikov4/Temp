package com.epam.hw1.web.controller;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.impl.EventBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.mockito.Mockito.verify;

/**
 * @author Yevhen_Larikov
 */
@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {
    private static final int EVENT_ID = 1;
    private static final int PAGE_SIZE = 1;
    private static final int PAGE_NUM = 1;
    public static final String EVENT_TITLE = "title";
    public static final Date EVENT_DAY = new Date();


    @Mock
    private BookingFacade bookingFacade;

    @InjectMocks
    private EventController controller;

    @Mock
    private EventBean event = new EventBean(EVENT_ID);

    @Test
    public void shouldInvokeFacadeGetEventById(){
        controller.getEventById(EVENT_ID);

        verify(bookingFacade).getEventById(EVENT_ID);
    }

    @Test
    public void shouldInvokeFacadeGetEventsByTitle(){
        controller.getEventsByTitle(EVENT_TITLE, PAGE_SIZE, PAGE_NUM);

        verify(bookingFacade).getEventsByTitle(EVENT_TITLE, PAGE_SIZE, PAGE_NUM);
    }

    @Test
    public void shouldInvokeFacadeGetEventsForDay(){
        controller.getEventsForDay(EVENT_DAY, PAGE_SIZE, PAGE_NUM);

        verify(bookingFacade).getEventsForDay(EVENT_DAY, PAGE_SIZE, PAGE_NUM);
    }

    @Test
    public void shouldInvokeFacadeCreateEvent(){
        controller.createEvent(event);

        verify(bookingFacade).createEvent(event);
    }

    @Test
    public void shouldInvokeFacadeUpdateEvent(){
        controller.updateEvent(event);

        verify(bookingFacade).updateEvent(event);
    }

    @Test
    public void shouldInvokeFacadeDeleteEvent(){
        controller.deleteEvent(EVENT_ID);

        verify(bookingFacade).deleteEvent(EVENT_ID);
    }
}
