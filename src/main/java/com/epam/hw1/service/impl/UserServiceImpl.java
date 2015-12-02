package com.epam.hw1.service.impl;

import com.epam.hw1.dao.UserDao;
import com.epam.hw1.dao.annotation.JpaImpl;
import com.epam.hw1.model.User;
import com.epam.hw1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <code>UserService</code> implementation.
 *
 * @author Yevhen_Larikov
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private UserDao userDao;

    /**
     * Injects <code>UserDao</code> into Service.
     *
     * @param userDao UserDao
     */
    @Autowired
    @JpaImpl
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserById(long userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userDao.getUsersByName(name,pageSize,pageNum);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    @Transactional
    public boolean deleteUser(long userId) {
        return userDao.deleteUser(userId);
    }
}
