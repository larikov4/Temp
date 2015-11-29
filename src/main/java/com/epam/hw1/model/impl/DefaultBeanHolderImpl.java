package com.epam.hw1.model.impl;

import com.epam.hw1.model.DefaultBeanHolder;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.User;
import org.springframework.stereotype.Component;

/**
 * <code>DefaultBeanHolder</code> implementation
 *
 * @author Yevhen_Larikov
 */
@Component
public class DefaultBeanHolderImpl implements DefaultBeanHolder {
    private User user;
    private Event event;

    @Override
    public User getDefaultUser() {
        return user;
    }

    @Override
    public void setDefaultUser(User user) {
        this.user = user;
    }

    @Override
    public Event getDefaultEvent() {
        return event;
    }

    @Override
    public void setDefaultEvent(Event event) {
        this.event = event;
    }
}
