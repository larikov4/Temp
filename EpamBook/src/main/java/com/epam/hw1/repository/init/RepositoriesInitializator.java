package com.epam.hw1.repository.init;

import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.UserBean;
import com.epam.hw1.repository.FriendRepository;
import com.epam.hw1.repository.TimelineRepository;
import com.epam.hw1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Yevhen_Larikov on 21.12.2015.
 */
@Service
public class RepositoriesInitializator {

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TimelineRepository timelineRepository;
    @Autowired
    private FriendRepository friendRepository;

    public RepositoriesInitializator() {
    }

    @PostConstruct
    public void fillRepositories() throws ParseException {
        List<String> names = Arrays.asList("Ivan", "Max", "Alex", "Petr");
        for(String name : names){
            userRepository.addUser(generateUser(name));
        }
        String firstUsername = names.get(0).toLowerCase();
        String secondUsername = names.get(1).toLowerCase();
        friendRepository.makeFriends(firstUsername, secondUsername);
        timelineRepository.addNote(firstUsername, new NoteBean("Hello world", firstUsername));
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
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
