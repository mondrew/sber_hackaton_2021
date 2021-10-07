package com.example.dchat.controller;

import com.example.dchat.model.ChatMessage;
import com.example.dchat.model.ChatUser;
import com.example.dchat.service.ChatAuthenticationService;
import com.example.dchat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatAuthenticationService chatAuthenticationService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        chatMessageService.sendMessage(chatMessage);
    }

    @GetMapping("/messages/{sender}/{recipient}")
    public List<ChatMessage> getMessages(@PathVariable String sender, @PathVariable String recipient) {
        return chatMessageService.getMessages(sender + "_" + recipient);
    }

    @GetMapping("/users")
    public List<ChatUser> getUsers() {
        return chatAuthenticationService.getAllUsers();
    }

    @GetMapping("/user/{login}")
    public ChatUser getUserByLogin(@PathVariable String login) {
        return chatAuthenticationService.getUserByLogin(login);
    }

    @PostMapping("/users")
    public ChatUser create(ChatUser user) {
        return chatAuthenticationService.registerNewUser(user);
    }
}
