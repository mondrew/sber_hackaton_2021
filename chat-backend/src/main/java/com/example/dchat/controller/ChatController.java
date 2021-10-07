package com.example.dchat.controller;

import com.example.dchat.model.ChatMessage;
import com.example.dchat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        chatMessageService.sendMessage(chatMessage);
    }

    @GetMapping("/messages/{sender}/{recipient}")
    public List<ChatMessage> getMessages(@PathVariable String sender, @PathVariable String recipient) {
        return chatMessageService.getMessages(sender + "_" + recipient);
    }
}
