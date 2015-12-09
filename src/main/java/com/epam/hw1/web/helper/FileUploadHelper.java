package com.epam.hw1.web.helper;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.impl.EventBean;
import com.epam.hw1.model.impl.TicketBean;
import com.epam.hw1.model.impl.UserBean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Class is responsible for persisting entities from JSON.
 *
 * @author Yevhen_Larikov
 */
@Service
public class FileUploadHelper {
    private static Logger LOG = Logger.getLogger(FileUploadHelper.class);
    private BookingFacade facade;
    private Map<String, NodeProcessor> processors;

    public FileUploadHelper() {
        initProcessors();
    }

    @Autowired
    public void setFacade(BookingFacade facade) {
        this.facade = facade;
    }

    /**
     * Persist entities from JSON file.
     * @param inputStream InputStream of JSON file
     * @return boolean whether file was successfully persisted
     */
    public boolean fillStorageFromJsonFile(InputStream inputStream) {
        if (inputStream == null) {
            LOG.error("Storage cannot be filled. Json string is null.");
            return false;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, JsonNode> root = mapper.readValue(inputStream, mapper.getTypeFactory()
                    .constructMapType(Map.class, String.class, JsonNode.class));
            for (Map.Entry<String, JsonNode> entry : root.entrySet()) {
                String entityPrefix = entry.getKey().replaceFirst("\\d+", StringUtils.EMPTY);
                String jsonNode = entry.getValue().toString();
                processors.get(entityPrefix).parseAndPersist(mapper, jsonNode);
            }
            return true;
        } catch (IOException e) {
            LOG.error("An exception while filling storage is occurred", e);
            return false;
        }
    }

    private void initProcessors() {
        processors = new HashMap<>();
        processors.put("user", (mapper, jsonNode) -> facade.createUser(mapper.readValue(jsonNode, UserBean.class)));
        processors.put("event", (mapper, jsonNode) -> facade.createEvent(mapper.readValue(jsonNode, EventBean.class)));
        processors.put("ticket", (mapper, jsonNode) -> {
            TicketBean ticket = mapper.readValue(jsonNode, TicketBean.class);
            facade.bookTicket(ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());
        });
    }

    /**
     * Interface is responsible for processing JSON node and save parsed entity to storage.
     */
    @FunctionalInterface
    private interface NodeProcessor {
        void parseAndPersist(ObjectMapper mapper, String node) throws IOException;
    }
}
