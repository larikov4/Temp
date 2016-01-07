package com.epam.hw1.repository.init;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.UserBean;
import com.epam.hw1.repository.FriendRepository;
import com.epam.hw1.repository.TimelineRepository;
import com.epam.hw1.repository.UserRepository;
import com.epam.hw1.service.FriendService;
import com.epam.hw1.service.TimelineService;
import com.epam.hw1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Component is used for initial filling repositories.
 * It should be used in demo purpose only.
 * <p>
 * @author Yevhen_Larikov on 21.12.2015.
 */
@Component
public class RepositoriesInitializator {
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    private UserService userService;
    @Autowired
    private TimelineService timelineService;
    @Autowired
    private FriendService friendService;

    /**
     * Fills each repository with beans to show main functionality of the application.
     */
    @PostConstruct
    public void fillRepositories() throws ParseException, UserNotFoundException {
        List<String> names = Arrays.asList("Ivan", "Max", "Alex", "Petr");
        for (String name : names) {
            userService.addUser(generateUser(name));
        }
        String firstUsername = names.get(0).toLowerCase();
        String secondUsername = names.get(1).toLowerCase();
        friendService.makeFriends(firstUsername, secondUsername);
        timelineService.addNote(firstUsername, new NoteBean("Hello world", firstUsername));
    }

    private UserBean generateUser(String name) throws ParseException {
        UserBean userBean = new UserBean();
        userBean.setDateOfBirth(generateDate());
        userBean.setName(name);
        userBean.setUsername(name.toLowerCase());
        return userBean;
    }

    private Date generateDate() throws ParseException {
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        int day = random.nextInt(27) + 1;
        int month = random.nextInt(11) + 1;
        calendar.setTime(SIMPLE_DATE_FORMAT.parse(day + "-" + month + "-1988"));
        return calendar.getTime();
    }
}
