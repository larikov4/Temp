package com.epam.hw1.repository.impl;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.model.UserBean;
import com.epam.hw1.repository.UserRepository;
import com.epam.hw1.repository.aggregate.UserAggregate;
import com.epam.hw1.repository.event.UserCreateEvent;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link UserRepository} implementation.
 *
 * @author Yevhen_Larikov on 20.12.2015.
 */
@Repository
public class UserRepositoryImpl implements UserRepository{
    private Map<String, UserCreateEvent> userCreationEvents = new HashMap<>();

    @Override
    public void addUser(long version, UserBean userBean){
        userCreationEvents.put(userBean.getUsername(), new UserCreateEvent(version, userBean));
    }

    @Override
    public UserBean getUser(String username){
        if(userCreationEvents.get(username)==null){
            return null;
        }
        UserAggregate userAggregate = new UserAggregate();
        userAggregate.onCreationEvent(userCreationEvents.get(username));
        return userAggregate.getUser();
    }

    @Override
    public void checkUserExistence(String username) throws UserNotFoundException {
        if(getUser(username)==null){
            throw new UserNotFoundException("Cannot find user. Username: " + username );
        }
    }
}
