package com.hicode.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    @GetMapping("/show")
    public String hello(){

        this.messagingTemplate.convertAndSend("/topic/reply/2", "Cám ơn quý khách đã mua sản phẩm của chúng tôi");

        return "Hello one 1";
    }
}
