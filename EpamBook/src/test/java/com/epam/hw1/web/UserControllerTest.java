package com.epam.hw1.web;

import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.UserBean;
import com.epam.hw1.service.impl.FriendServiceImpl;
import com.epam.hw1.service.impl.TimelineServiceImpl;
import com.epam.hw1.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yevhen_Larikov on 21.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
@WebAppConfiguration
public class UserControllerTest {

    public static final String NEW_USERNAME = "newUsername";
    public static final String EXISTING_USERNAME = "ivan";
    public static final String NEW_NOTE = "new note";
    public static final String NEW_FRIEND_USERNAME = "Alex";
    public static final String FRIEND_USERNAME = "max";

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private TimelineServiceImpl timelineService;
    @Autowired
    private FriendServiceImpl friendService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void shouldCreateUser() throws Exception {
        UserBean newUser = new UserBean();
        newUser.setDateOfBirth(generateDate());
        newUser.setName("newUserName");
        newUser.setUsername(NEW_USERNAME);

        System.out.println(mapper.writeValueAsString(newUser));

        mockMvc.perform(post("/createUser")
                .content(mapper.writeValueAsString(newUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(newUser, userService.getUser(NEW_USERNAME));
    }

    @Test
    public void shouldAddNote() throws Exception {
        NoteBean newNote = new NoteBean(NEW_NOTE, EXISTING_USERNAME);
        int initialTimelineSize = timelineService.getTimelineBean(EXISTING_USERNAME).getTimeline().size();

        mockMvc.perform(post("/user/" + EXISTING_USERNAME + "/timeline/")
                .content(mapper.writeValueAsString(newNote))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<NoteBean> notes = timelineService.getTimelineBean(EXISTING_USERNAME).getTimeline();
        assertEquals(newNote, notes.get(initialTimelineSize));
    }

    @Test
    public void shouldMakeFriends() throws Exception {
        assertFalse(friendService.isFriends(EXISTING_USERNAME, NEW_FRIEND_USERNAME));
        UserBean newUser = new UserBean();
        newUser.setUsername(NEW_FRIEND_USERNAME);

        mockMvc.perform(post("/user/" + EXISTING_USERNAME + "/friend/")
                .content(mapper.writeValueAsString(newUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertTrue(friendService.isFriends(EXISTING_USERNAME, NEW_FRIEND_USERNAME));
    }

    @Test
    public void shouldAddNoteToFriendTimeline() throws Exception {
        NoteBean newNote = new NoteBean(NEW_NOTE, FRIEND_USERNAME);
        int initialTimelineSize = timelineService.getTimelineBean(EXISTING_USERNAME).getTimeline().size();

        mockMvc.perform(post("/user/" + FRIEND_USERNAME + "/friend/"+ EXISTING_USERNAME +"/timeline/")
                .content(mapper.writeValueAsString(newNote))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<NoteBean> notes = timelineService.getTimelineBean(EXISTING_USERNAME).getTimeline();
        assertEquals(newNote, notes.get(initialTimelineSize));
    }

    @Test
    public void shouldGetTimeline() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/" + EXISTING_USERNAME + "/timeline/"))
                .andExpect(status().isOk()).andReturn();

        String parsedNote = mapper.writeValueAsString(timelineService.getTimelineBean(EXISTING_USERNAME));
        assertEquals(parsedNote, result.getResponse().getContentAsString());
    }

    @Test
    public void shouldGetFriends() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/" + FRIEND_USERNAME + "/friend/"))
                .andExpect(status().isOk()).andReturn();

        String parsedFriends = mapper.writeValueAsString(friendService.getFriends(FRIEND_USERNAME));
        assertEquals(parsedFriends, result.getResponse().getContentAsString());
    }

    @Test
    public void shouldGetFriendTimeline() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/" + FRIEND_USERNAME + "/friend/"+ EXISTING_USERNAME +"/timeline/"))
                .andExpect(status().isOk()).andReturn();

        String parsedTimeline = mapper.writeValueAsString(timelineService.getTimelineBean(EXISTING_USERNAME));
        assertEquals(parsedTimeline, result.getResponse().getContentAsString());
    }

    private Date generateDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}