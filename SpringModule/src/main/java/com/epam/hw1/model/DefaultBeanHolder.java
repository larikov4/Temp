package com.epam.hw1.model;

/**
 * Holder of default entities. Can be used instead of passed parameters.
 *
 * @author Yevhen_Larikov
 */
public interface DefaultBeanHolder {

    User getDefaultUser();

    void setDefaultUser(User user);

    Event getDefaultEvent();

    void setDefaultEvent(Event event);
}
