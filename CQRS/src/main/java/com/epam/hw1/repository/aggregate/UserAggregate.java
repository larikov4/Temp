package com.epam.hw1.repository.aggregate;

import com.epam.hw1.model.UserBean;
import com.epam.hw1.repository.event.UserCreateEvent;

/**
 * Aggregate root for users. Should be used to apply events on it.
 *
 * @author Yevhen_Larikov on 06.01.2016.
 */
public class UserAggregate {
    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    /**
     * Applies passed event on itself.
     *
     * @param event event to apply on aggregate
     */
    public void onCreationEvent(UserCreateEvent event){
        user = new UserBean(event.getUser());
    }
}
