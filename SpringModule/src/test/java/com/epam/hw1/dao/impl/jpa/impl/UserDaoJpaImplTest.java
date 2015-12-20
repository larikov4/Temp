package com.epam.hw1.dao.impl.jpa.impl;

import com.epam.hw1.dao.UserDao;
import com.epam.hw1.dao.annotation.JpaImpl;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Yevhen_Larikov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml", "classpath:test-datasource-config.xml"})
@WebAppConfiguration
public class UserDaoJpaImplTest {
    private static final int EXISTING_USER_ID = 1;
    private static final int NEW_USER_ID = 20;
    private static final String EXISTING_USER_NAME = "Ivan1";
    private static final String NEW_USER_NAME = "Ivan20";
    private static final String EXISTING_USER_EMAIL = "ivan1@email.com";
    private static final String NEW_USER_EMAIL = "ivan20@email.com";
    private static final int PAGE_SIZE = 2;
    private static final int PAGE_NUM = 1;

    @Autowired
    @JpaImpl
    private UserDao jpaUserDao;

    private User existingUser;
    private User newUser;

    @Before
    public void setUp(){
        existingUser = new UserBean(EXISTING_USER_ID);
        existingUser.setName(EXISTING_USER_NAME);
        existingUser.setEmail(EXISTING_USER_EMAIL);

        newUser = new UserBean();
        newUser.setName(NEW_USER_NAME);
        newUser.setEmail(NEW_USER_EMAIL);
    }

    @Test
    public void shouldReturnUserById(){
        assertEquals(existingUser, jpaUserDao.getUserById(EXISTING_USER_ID));
    }

    @Test
    public void shouldReturnUserByEmail(){
        assertEquals(existingUser, jpaUserDao.getUserByEmail(EXISTING_USER_EMAIL));
    }

    @Test
    public void shouldReturnListWhenUsersIsEnough(){
        List<User> usersByName = jpaUserDao.getUsersByName(EXISTING_USER_NAME, PAGE_SIZE, PAGE_NUM);
        assertEquals(PAGE_SIZE, usersByName.size());
        assertEquals(existingUser, usersByName.get(0));
    }

    @Test
    public void shouldReturnEmptyListWhenUsersIsEnough(){
        List<User> usersByName = jpaUserDao.getUsersByName(NEW_USER_NAME, PAGE_SIZE, PAGE_NUM);
        assertTrue(usersByName.isEmpty());
    }

    @Test
    @DirtiesContext
    public void shouldCreateUser(){
        assertEquals(newUser, jpaUserDao.createUser(newUser));
        assertNotNull(newUser.getId());
        assertEquals(newUser, jpaUserDao.getUserById(newUser.getId()));
    }

    @Test
    @DirtiesContext
    public void shouldUpdateUser(){
        newUser.setId(EXISTING_USER_ID);
        assertEquals(newUser, jpaUserDao.updateUser(newUser));
        assertEquals(newUser, jpaUserDao.getUserById(EXISTING_USER_ID));
    }

    @Test
    @DirtiesContext
    public void shouldRemoveUserById(){
        assertTrue(jpaUserDao.deleteUser(EXISTING_USER_ID));
        assertNull(jpaUserDao.getUserById(EXISTING_USER_ID));
    }

    @Test
    @DirtiesContext
    public void shouldReturnFalseWhenRemovingNotExistingUser(){
        assertFalse(jpaUserDao.deleteUser(NEW_USER_ID));
    }
}
