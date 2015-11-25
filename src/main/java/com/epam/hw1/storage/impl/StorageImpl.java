package com.epam.hw1.storage.impl;

import com.epam.hw1.model.impl.EventBean;
import com.epam.hw1.model.impl.TicketBean;
import com.epam.hw1.model.impl.UserBean;
import com.epam.hw1.storage.Storage;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>Storage</code> interface implementation.
 *
 * @author Yevhen_Larikov
 */
@Repository("storage")
public class StorageImpl implements Storage {
    private static final Logger LOG = Logger.getLogger(StorageImpl.class);

    private Map<String, Object> map = new HashMap<>();
    private Map<String, Class> typesMap = new HashMap<>();

    /**
     * Creates empty object with filled typesMap containing <code>Class</code> name and
     * <code>Class</code> object which can be stored in created object's inner map.
     * typesMap is used to instantiate parsed from Json string objects.
     */
    public StorageImpl() {
        initTypesMap();
    }

    @Override
    public <T> T get(String key) {
        return (T) map.get(key);
    }

    @Override
    public Map<String, Object> getAll() {
        return new HashMap<>(map);
    }

    @Override
    public <T> T put(String key, T value) {
        map.put(key, value);
        return value;
    }

    @Override
    public boolean remove(String key) {
        return map.remove(key) != null;
    }

    @Override
    public void fillStorageFromJson(String json) {
        if (json == null) {
            LOG.error("Storage cannot be filled. Json string is null.");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, JsonNode> root = mapper.readValue(json, mapper.getTypeFactory()
                    .constructMapType(Map.class, String.class, JsonNode.class));
            for (Map.Entry<String, JsonNode> entry : root.entrySet()) {
                String entityPrefix = entry.getKey().replaceFirst("\\d+", StringUtils.EMPTY);
                Object entity = mapper.readValue(entry.getValue(), typesMap.get(entityPrefix));
                map.put(entry.getKey(), entity);
            }
        } catch (IOException e) {
            LOG.error("An exception while filling storage is occurred", e);
        }
    }

    private void initTypesMap() {
        typesMap.put("user", UserBean.class);
        typesMap.put("event", EventBean.class);
        typesMap.put("ticket", TicketBean.class);
    }
}
