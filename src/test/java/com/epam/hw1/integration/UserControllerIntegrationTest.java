package com.epam.hw1.integration;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Yevhen_Larikov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml", "classpath:test-datasource-config.xml"})
@WebAppConfiguration
public class UserControllerIntegrationTest {
    private static final long USER_ID = 1;
    private static final long ANOTHER_USER_ID = 8;
    private static final String USER_EMAIL = "ivan1@email.com";
    private static final String USER_NAME = "Ivan1";
    private static final int PAGE_NUM = 1;
    private static final int PAGE_SIZE = 5;
    public static final String NEW_EMAIL = "notExistingEmail@email.com";

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BookingFacade facade;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        MvcResult result = mockMvc.perform(get("/users?id=" + USER_ID))
                .andExpect(status().isOk()).andReturn();
        String parsedUser = mapper.writeValueAsString(facade.getUserById(USER_ID));
        assertEquals(parsedUser, result.getResponse().getContentAsString());
    }

    @Test
    public void shouldReturnUserByEmail() throws Exception {
        MvcResult result = mockMvc.perform(get("/users?email=" + USER_EMAIL))
                .andExpect(status().isOk()).andReturn();
        String parsedUser = mapper.writeValueAsString(facade.getUserByEmail(USER_EMAIL));
        assertEquals(parsedUser, result.getResponse().getContentAsString());
    }

    @Test
    public void shouldReturnDeleteUserById() throws Exception {
        assertNotNull(facade.getUserById(ANOTHER_USER_ID));
        mockMvc.perform(delete("/users/" + ANOTHER_USER_ID))
                .andExpect(status().isOk()).andReturn();

        assertNull(facade.getUserById(ANOTHER_USER_ID));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        User newUser = new UserBean();
        newUser.setEmail(NEW_EMAIL);
        newUser.setId(111);
        newUser.setName(NEW_EMAIL);
        MvcResult mvcResult = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(mapper.writeValueAsString(facade.getUserByEmail(NEW_EMAIL)),
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void shouldReturnUserByName() throws Exception {
        MvcResult result = mockMvc.perform(get("/users?name=" + USER_NAME))
                .andExpect(status().isOk()).andReturn();
        String parsedUser = mapper.writeValueAsString(facade.getUsersByName(USER_NAME, PAGE_SIZE, PAGE_NUM));
        assertEquals(parsedUser, result.getResponse().getContentAsString());
    }
}
