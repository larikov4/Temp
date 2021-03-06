package com.epam.hw1.web.controller;

import com.epam.hw1.model.UserBean;
import com.epam.hw1.service.FriendService;
import com.epam.hw1.web.controller.handler.ExceptionHandlingController;
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

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yevhen_Larikov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
@WebAppConfiguration
public class FriendControllerTest {
    public static final String NEW_FRIEND_USERNAME = "alex";
    public static final String EXISTING_USERNAME = "ivan";
    public static final String FRIEND_USERNAME = "max";
    public static final String NOT_EXISTING_USERNAME = "not existing ivan";

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FriendService friendService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mapper = new ObjectMapper();
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
    public void shouldGetFriends() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/" + FRIEND_USERNAME + "/friend/"))
                .andExpect(status().isOk()).andReturn();

        String parsedFriends = mapper.writeValueAsString(friendService.getFriends(FRIEND_USERNAME));
        assertEquals(parsedFriends, result.getResponse().getContentAsString());
    }

    @Test
    public void shouldReturnErrorWhenGetNotExistingUserFriends() throws Exception {
        mockMvc.perform(get("/user/" + NOT_EXISTING_USERNAME + "/friend/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString(ExceptionHandlingController.ERROR_MESSAGE_ATTRIBUTE)))
                .andExpect(content().string(containsString(NOT_EXISTING_USERNAME)))
                .andExpect(content().string(containsString("not exist")));
    }

}