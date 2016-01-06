package com.epam.hw1.repository.impl;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.model.UserBean;
import com.epam.hw1.repository.UserRepository;
import com.epam.hw1.repository.aggregate.UserAggregate;
import com.epam.hw1.repository.event.UserCreateEvent;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link UserRepository} implementation.
 *
 * Created by Yevhen_Larikov on 20.12.2015.
 */
@Repository
public class UserRepositoryImpl implements UserRepository{
    private Map<String, UserCreateEvent> creationEvents = new HashMap<>();
    private AtomicLong lastVersionId = new AtomicLong(0);

    @Override
    public void addUser(UserBean userBean){
        //Atomic operation is down here. There is nothing compare and swap so that just use atomic.
        creationEvents.put(userBean.getUsername(), new UserCreateEvent(lastVersionId.incrementAndGet(), userBean));
    }

    @Override
    public UserBean getUser(String username){
        UserAggregate userAggregate = new UserAggregate();
        userAggregate.onCreationEvent(creationEvents.get(username));
        return userAggregate.getUser();
    }

    @Override
    public void checkUserExistence(String username) throws UserNotFoundException {
        if(getUser(username)==null){
            throw new UserNotFoundException("Cannot find user. Username: " + username );
        }
    }
}
