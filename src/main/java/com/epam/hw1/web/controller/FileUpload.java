package com.epam.hw1.web.controller;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.impl.EventBean;
import com.epam.hw1.model.impl.TicketBean;
import com.epam.hw1.model.impl.UserBean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Yevhen_Larikov
 */
@Controller
@RequestMapping("/upload")
public class FileUpload {
    private static Logger LOG = Logger.getLogger(IndexController.class);
    private static final String VIEW_NAME = "exceptionView";
    private static final String ERROR_MESSAGE_ATTRIBUTE = "message";

    private BookingFacade facade;

    @Autowired
    public void setFacade(BookingFacade facade) {
        this.facade = facade;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView uploadForm() {
        return new ModelAndView("uploadFileView");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView handleFormUpload(@RequestParam("file") MultipartFile file) throws IOException {
        LOG.info("Uploading file...");
        if(file.isEmpty()){
            return new ModelAndView(VIEW_NAME, new ModelMap(ERROR_MESSAGE_ATTRIBUTE, "Uploaded file is empty"));
        }
        InputStream inputStream = file.getInputStream();
        fillStorageFromJson(inputStream);
        LOG.info("File was uploaded successfully");
        return new ModelAndView("successView");
    }

    public void fillStorageFromJson(InputStream json) {
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
                String jsonNode = entry.getValue().toString();
                switch (entityPrefix){
                    case "user":
                        facade.createUser(mapper.readValue(jsonNode, UserBean.class));
                        break;
                    case "event":
                        facade.createEvent(mapper.readValue(jsonNode, EventBean.class));
                        break;
                    case "ticket":
                        TicketBean ticket = mapper.readValue(jsonNode, TicketBean.class);
                        facade.bookTicket(ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());
                        break;
                }
            }
        } catch (IOException e) {
            LOG.error("An exception while filling storage is occurred", e);
        }
    }
}
