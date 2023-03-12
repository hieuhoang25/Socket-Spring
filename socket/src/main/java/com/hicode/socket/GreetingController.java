package com.hicode.socket;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;

@RestController
@Slf4j
public class GreetingController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    // use to chat with together
//    @MessageMapping("/message/{id}")
//    @SendTo("/topic/reply/{id}")
//    public String processMessageFromClient(@Payload String message, @DestinationVariable("id") String id) throws Exception {
//        log.info(id);
//        String name = new Gson().fromJson(message, Map.class).get("name").toString();
//        // if you use below line , comment  @SendTo("/topic/reply/{id}")
//        this.messagingTemplate.convertAndSend("/topic/reply/3", name);
//
//        return "Hello" + name;
//    }

    @MessageExceptionHandler
    public String handleException(Throwable exception) {
        messagingTemplate.convertAndSend("/errors", exception.getMessage());
        return exception.getMessage();
    }
}
