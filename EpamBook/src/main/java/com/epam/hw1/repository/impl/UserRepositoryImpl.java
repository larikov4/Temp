package com.epam.hw1.repository.impl;

import com.epam.hw1.model.UserBean;
import com.epam.hw1.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yevhn on 20.12.2015.
 */
@Repository
public class UserRepositoryImpl implements UserRepository{
    private Map<String, UserBean> users = new HashMap<>();

    @Override
    public void addUser(UserBean userBean){
        users.put(userBean.getUsername(), userBean);
    }

    @Override
    public UserBean getUser(String username){
        return users.get(username);
    }

    @Override
    public boolean exists(String username){
        return users.containsKey(username);
    }

}
