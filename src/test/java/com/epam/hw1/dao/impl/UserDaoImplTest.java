package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.UserDao;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserBean;
import com.epam.hw1.storage.Storage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author Yevhen_Larikov
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {
    private static final long USER_ID = 1L;
    private static final String USER_ID_WITH_PREFIX = UserDaoImpl.USER_PREFIX + USER_ID;
    private static final String USER_NAME = "testName";
    private static final String USER_EMAIL = "testEmail";
    private static final String ANOTHER_NAME = "testName1";
    private static final int PAGE_SIZE = 2;
    private static final int PAGE_NUM = 1;
    private static final int DEFAULT_USER_AMOUNT = 2;

    @Mock
    private Storage storage;

    @Mock
    private User user;

    @InjectMocks
    private UserDao userDao = new UserDaoImpl();

    @Test
    public void shouldReturnNullWhenThereIsNoUserWithSuchEmail() {
        Map<String, Object> users = generateUsers(DEFAULT_USER_AMOUNT);
        when(storage.getAll()).thenReturn(spy(users));

        assertNull(userDao.getUserByEmail(USER_EMAIL));
    }

    @Test
    public void shouldReturnFullListWhenUsersIsEnough() {
        Map<String, Object> users = generateUsers(PAGE_SIZE * PAGE_NUM);
        when(storage.getAll()).thenReturn(spy(users));

        assertEquals(PAGE_SIZE, userDao.getUsersByName(USER_NAME, PAGE_SIZE, PAGE_NUM).size());
    }

    @Test
    public void shouldReturnNotFullListWhenUsersIsNotEnough() {
        Map<String, Object> users = generateUsers(PAGE_SIZE * PAGE_NUM - PAGE_SIZE / 2);
        when(storage.getAll()).thenReturn(spy(users));

        assertEquals(PAGE_SIZE / 2, userDao.getUsersByName(USER_NAME, PAGE_SIZE, PAGE_NUM).size());
    }

    @Test
    public void shouldReturnEmptyListWhenNoUsersWereFound() {
        Map<String, Object> users = new HashMap<>();
        when(storage.getAll()).thenReturn(spy(users));

        assertEquals(Collections.emptyList(), userDao.getUsersByName(USER_NAME, PAGE_SIZE, PAGE_NUM));
    }

    @Test
    public void shouldPutUserIntoStorage() {
        when(storage.put(USER_ID_WITH_PREFIX, user)).thenReturn(user);
        when(user.getId()).thenReturn(USER_ID);

        assertEquals(user, userDao.createUser(user));
        Mockito.verify(storage).put(USER_ID_WITH_PREFIX, user);
    }

    @Test
    public void shouldNotPutUserWithDuplicatingEmailIntoStorage() {
        Map<String, Object> users = generateUsers(1);
        when(storage.getAll()).thenReturn(spy(users));
        User generatedUser = (User) users.get(USER_ID_WITH_PREFIX);

        assertEquals(generatedUser, userDao.createUser(generatedUser));
        assertNull(userDao.createUser(generatedUser));
    }

    @Test
    public void shouldUpdateUserInStorage() {
        when(storage.put(USER_ID_WITH_PREFIX, user)).thenReturn(user);
        when(user.getId()).thenReturn(USER_ID);

        assertEquals(user, userDao.updateUser(user));
        Mockito.verify(storage).put(USER_ID_WITH_PREFIX, user);
    }

    @Test
    public void shouldReturnUserById() {
        when(storage.get(USER_ID_WITH_PREFIX)).thenReturn(user);

        assertEquals(user, userDao.getUserById(USER_ID));
        Mockito.verify(storage).get(USER_ID_WITH_PREFIX);
    }

    @Test
    public void shouldRemoveUserFromStorage() {
        when(storage.remove(USER_ID_WITH_PREFIX)).thenReturn(true);

        userDao.deleteUser(USER_ID);
        Mockito.verify(storage).remove(USER_ID_WITH_PREFIX);
    }

    @Test
    public void shouldReturnUserByEmail() {
        Map<String, Object> users = generateUsers(DEFAULT_USER_AMOUNT);
        when(storage.getAll()).thenReturn(spy(users));

        assertEquals(generateUser((int)USER_ID, USER_NAME), userDao.getUserByEmail(USER_EMAIL + USER_ID));
    }

    private Map<String, Object> generateUsers(int amount) {
        Map<String, Object> users = new HashMap<>();
        for (int i = 0; i < amount; i++) {
            users.put(UserDaoImpl.USER_PREFIX + i, generateUser(i, USER_NAME));
        }
        addInvalidUsers(users);
        return users;
    }

    private Map<String, Object> addInvalidUsers(Map<String, Object> users) {
        int k=0;
        users.put(UserDaoImpl.USER_PREFIX + --k, generateUser(k, ANOTHER_NAME));
        users.put(UserDaoImpl.USER_PREFIX + --k, generateUser(k, null));
        return users;
    }

    private User generateUser(int number, String name) {
        User user = new UserBean();
        user.setId(number);
        user.setName(name);
        user.setEmail(USER_EMAIL + number);
        return user;
    }
}
