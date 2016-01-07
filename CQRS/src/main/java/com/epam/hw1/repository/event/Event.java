package com.epam.hw1.repository.event;

/**
 * Event with own version id. Should be saved and retrieved from repositories.
 *
 * @author Yevhen_Larikov on 06.01.2016.
 */
public interface Event {
    long getVersionId();
}
