package com.epam.hw1.repository.aggregate;

import com.epam.hw1.model.UserBean;
import com.epam.hw1.repository.event.UserCreateEvent;

/**
 * Created by Yevhn on 06.01.2016.
 */
public class UserAggregate {
    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void onCreationEvent(UserCreateEvent event){
        user = new UserBean(event.getUser());
    }
}
