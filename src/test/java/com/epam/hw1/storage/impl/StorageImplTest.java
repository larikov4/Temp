package com.epam.hw1.storage.impl;

import com.epam.hw1.model.Event;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.EventBean;
import com.epam.hw1.model.impl.UserBean;
import com.epam.hw1.storage.Storage;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Yevhen_Larikov
 */

public class StorageImplTest {
    private static final long USER_ID = 1L;
    private static final String USER_ID_WITH_PREFIX = "user" + USER_ID;
    private static final long EVENT_ID = 2L;
    private static final String EVENT_ID_WITH_PREFIX = "event" + EVENT_ID;

    private Storage storage = new StorageImpl();
    private User user = new UserBean();
    private Event event = new EventBean();

    @Before
    public void setUp() {
        user.setId(USER_ID);
        user.setName("userName");
        user.setEmail("userName@email.com");
        event.setId(EVENT_ID);
        event.setTitle("eventTitle");
        event.setDate(new Date());
    }

    @Test
    public void shouldReturnInsertedValue() {
        storage.put(USER_ID_WITH_PREFIX, user);

        assertEquals(user, storage.get(USER_ID_WITH_PREFIX));
    }

    @Test
    public void shouldReturnAllInsertedValues() {
        int cyclesAmount = 5;
        for (int i = 0; i < cyclesAmount; i++) {
            storage.put(String.valueOf(USER_ID + i), new UserBean());
        }

        assertEquals(cyclesAmount, storage.getAll().size());
    }

    @Test
    public void shouldRemoveValue() {
        storage.put(USER_ID_WITH_PREFIX, user);
        storage.remove(USER_ID_WITH_PREFIX);

        assertTrue(storage.getAll().isEmpty());
    }

    @Test
    public void shouldDeserializeEntitiesFromString() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put(USER_ID_WITH_PREFIX, user);
        map.put(EVENT_ID_WITH_PREFIX, event);
        String jsonMap = new ObjectMapper().writeValueAsString(map);

        storage.fillStorageFromJson(jsonMap);
        assertEquals(user, storage.get(USER_ID_WITH_PREFIX));
        assertEquals(event, storage.get(EVENT_ID_WITH_PREFIX));
    }
}
