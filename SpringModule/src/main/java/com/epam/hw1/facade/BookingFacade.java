package com.epam.hw1.facade;


import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.User;

import java.util.Date;
import java.util.List;

/**
 * Groups together all operations related to tickets booking.
 * Created by maksym_govorischev.
 */
public interface BookingFacade {

    /**
     * Gets event by its eventId.
     * @param eventId Id of the Event.
     * @return Event.
     */
    Event getEventById(long eventId);

    /**
     * Get list of events by matching title. Title is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     * @param title Event title or it's part.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    List<Event> getEventsByTitle(String title, int pageSize, int pageNum);

    /**
     * Get list of events for specified day.
     * In case nothing was found, empty list is returned.
     * @param day Date object from which day information is extracted.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    List<Event> getEventsForDay(Date day, int pageSize, int pageNum);

    /**
     * Creates new event. Event eventId should be auto-generated.
     * @param event Event data.
     * @return Created Event object.
     */
    Event createEvent(Event event);

    /**
     * Updates event using given data.
     * @param event Event data for update. Should have eventId set.
     * @return Updated Event object.
     */
    Event updateEvent(Event event);

    /**
     * Deletes event by its eventId.
     * @param eventId Event eventId.
     * @return Flag that shows whether event has been deleted.
     */
    boolean deleteEvent(long eventId);

    /**
     * Gets user by its eventId.
     * @param userId Id of the Event.
     * @return User.
     */
    User getUserById(long userId);

    /**
     * Gets user by its email. Email is strictly matched.
     * @return User.
     */
    User getUserByEmail(String email);

    /**
     * Get list of users by matching name. Name is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     * @param name Users name or it's part.
     * @param pageSize Pagination param. Number of users to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of users.
     */
    List<User> getUsersByName(String name, int pageSize, int pageNum);

    /**
     * Creates new user. User eventId should be auto-generated.
     * @param user User data.
     * @return Created User object.
     */
    User createUser(User user);

    /**
     * Updates user using given data.
     * @param user User data for update. Should have eventId set.
     * @return Updated User object.
     */
    User updateUser(User user);

    /**
     * Deletes user by its eventId.
     * @param userId User eventId.
     * @return Flag that shows whether user has been deleted.
     */
    boolean deleteUser(long userId);

    /**
     * Book ticket for a specified event on behalf of specified user.
     * @param userId User Id.
     * @param eventId Event Id.
     * @param place Place number.
     * @param category Service category.
     * @return Booked ticket object.
     * @throws IllegalStateException if this place has already been booked.
     */
    Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category);

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order.
     * @param user User
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     * @param event Event
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);

    /**
     * Cancel ticket with a specified eventId.
     * @param ticketId Ticket eventId.
     * @return Flag whether anything has been canceled.
     */
    boolean cancelTicket(long ticketId);

    /**
     * Puts passed amount of money to User's account.
     * @param userId user id
     * @param amount amount of money to refill
     * @return boolean whether account was refilled
     */
    boolean refillAccount(long userId, double amount);

    /**
     * Sets default user to use instead of passed parameter to getBookingTickets method.
     * @param user default user
     */
    void setDefaultUser(User user);

    /**
     * Sets default event to use instead of passed parameter to getBookingTickets method.
     * @param event default event
     */
    void setDefaultEvent(Event event);

    /**
     * Unmarhal tickets from xml file and insert them as a batch to repository.
     * Money from user account won't be withdraw.
     * @param filename xml file name
     * @return boolean whether batch was inserted
     */
    boolean insertTicketsFromXml(String filename);
}
