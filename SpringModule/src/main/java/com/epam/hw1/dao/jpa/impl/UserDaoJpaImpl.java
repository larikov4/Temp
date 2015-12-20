package com.epam.hw1.dao.jpa.impl;

import com.epam.hw1.dao.UserDao;
import com.epam.hw1.dao.annotation.JpaImpl;
import com.epam.hw1.dao.jpa.UserDaoJpa;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <code>UserDao</code> implementation using JPA.
 *
 * @author Yevhen_Larikov
 */
@Repository
@JpaImpl
public class UserDaoJpaImpl implements UserDao {
    private UserDaoJpa userDaoJpa;

    @Autowired
    public void setUserDaoJpa(UserDaoJpa userDaoJpa) {
        this.userDaoJpa = userDaoJpa;
    }

    @Override
    public User getUserById(long userId) {
        return userDaoJpa.findOne(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDaoJpa.findByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userDaoJpa.findByName(name, new PageRequest(pageNum - 1, pageSize));
    }

    @Override
    public User createUser(User user) {
        return userDaoJpa.save((UserBean) user);
    }

    @Override
    public User updateUser(User user) {
        return userDaoJpa.save((UserBean) user);
    }

    @Override
    public boolean deleteUser(long userId) {
        boolean exists = userDaoJpa.exists(userId);
        if (exists) {
            userDaoJpa.delete(userId);
        }
        return exists;
    }
}
