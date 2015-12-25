package ua.nure.larikov.chat.chathandling;

import ua.nure.larikov.chat.entities.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class, configurator = ServletAwareConfig.class)
public class ChatEndpoint {
    public static final String NAME = "name";
    private final Logger log = Logger.getLogger(getClass().getName());

    @OnOpen
    public void open(final Session session, EndpointConfig config) {
        log.info("session openend ");
        User user = (User) config.getUserProperties().get("user");
        session.getUserProperties().put(NAME, user.getLogin());
    }

    @OnMessage
    public void onMessage(final Session session, final ChatMessage chatMessage) {
        log.info("enter sendmessage ");
        try {
            chatMessage.setSender((String) session.getUserProperties().get(NAME));
            log.info("sendmessage " + chatMessage);
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen()) {

                    log.info("sendmessage to next" + chatMessage);
                    s.getBasicRemote().sendObject(chatMessage);
                }
            }
        } catch (IOException | EncodeException e) {
            log.log(Level.WARNING, "onMessage failed", e);
        }
    }
}
