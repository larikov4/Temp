package com.epam.hw1.repository.event;

import com.epam.hw1.model.UserBean;

/**
 * Event on user creation.
 *
 * @author Yevhen_Larikov on 05.01.2016.
 */
public class UserCreateEvent implements Event{
    private long versionId;
    private UserBean user;

    public UserCreateEvent(long versionId, UserBean user) {
        this.versionId = versionId;
        this.user = user;
    }

    public UserBean getUser() {
        return user;
    }

    @Override
    public long getVersionId() {
        return versionId;
    }
}
