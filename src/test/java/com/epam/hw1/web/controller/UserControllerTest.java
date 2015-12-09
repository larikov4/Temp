package com.epam.hw1.web.controller;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.impl.UserBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * @author Yevhen_Larikov
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    private static final int USER_ID = 1;
    private static final int PAGE_SIZE = 1;
    private static final int PAGE_NUM = 1;
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "name@email.com";
    public static final Date USER_DAY = new Date();


    @Mock
    private BookingFacade bookingFacade;

    @InjectMocks
    private UserController controller;

    @Mock
    private UserBean user = new UserBean(USER_ID);

    @Test
    public void shouldInvokeFacadeGetUserById(){
        controller.getUserById(USER_ID);

        verify(bookingFacade).getUserById(USER_ID);
    }
    
    @Test
    public void shouldInvokeFacadeGetUserByEmail(){
        controller.getUserByEmail(USER_EMAIL);

        verify(bookingFacade).getUserByEmail(USER_EMAIL);
    }

    @Test
    public void shouldInvokeFacadeGetUsersByName(){
        controller.getUsersByName(USER_NAME, PAGE_SIZE, PAGE_NUM);

        verify(bookingFacade).getUsersByName(USER_NAME, PAGE_SIZE, PAGE_NUM);
    }

    @Test
    public void shouldInvokeFacadeGetUsersByNameAndSetProperView(){
        assertEquals(UserController.ENTITIES_LIST_VIEW,
                controller.getUsersByNameVisual(USER_NAME, PAGE_SIZE, PAGE_NUM).getViewName());

        verify(bookingFacade).getUsersByName(USER_NAME, PAGE_SIZE, PAGE_NUM);
    }

    @Test
    public void shouldInvokeFacadeCreateUserCreate(){
        controller.createUser(user);

        verify(bookingFacade).createUser(user);
    }

    @Test
    public void shouldInvokeFacadeUpdateUserCreate(){
        controller.updateUser(user);

        verify(bookingFacade).updateUser(user);
    }

    @Test
    public void shouldInvokeFacadeDeleteUserCreate(){
        controller.deleteUser(USER_ID);

        verify(bookingFacade).deleteUser(USER_ID);
    }
}
