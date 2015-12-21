package com.epam.hw1.service.impl;

import com.epam.hw1.model.UserBean;
import com.epam.hw1.repository.UserRepository;
import com.epam.hw1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yevhn on 20.12.2015.
 */
@Repository
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(UserBean userBean){
        userRepository.addUser(userBean);
    }

    @Override
    public UserBean getUser(String username){
        return userRepository.getUser(username);
    }

}
