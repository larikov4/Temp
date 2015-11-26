package com.epam.hw1;

import com.epam.hw1.dao.UserDao;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserBean;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Yevhen_Larikov
 */
public class UserDaoImplTest {

    private static ApplicationContext context;

    @BeforeClass
    public static void setUp1(){
        context = new ClassPathXmlApplicationContext("test-spring-config.xml");

    }

    @Test
    public void should(){
        UserDao userDao = context.getBean(UserDao.class);
//        UserBean user = new UserBean();
//        user.setId(15);
//        user.setName("name");
//        user.setEmail("email");
//        userDao.createUser(user);
        User userDB = userDao.getUserById(1);
        System.out.println(userDB);
    }

}
