package com.example.dchat.controller;

import com.example.dchat.model.ChatMessage;
import com.example.dchat.model.ChatUser;
import com.example.dchat.service.ChatAuthenticationService;
import com.example.dchat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatAuthenticationService chatAuthenticationService;

    @GetMapping("/messages/{sender}/{recipient}")
    public List<ChatMessage> getMessagesByChatId(@PathVariable String sender, @PathVariable String recipient) {
        return chatMessageService.getMessages(sender + "_" + recipient);
    }

    @GetMapping("/messages")
    public List<ChatMessage> getAllMessages() {
        return chatMessageService.getAllMessages();
    }

    @PostMapping("/messages")
    public ChatMessage createMessage(@RequestBody ChatMessage chatMessage) {
        return chatMessageService.sendMessage(chatMessage);
    }

    @GetMapping("/users")
    public List<ChatUser> getUsers() {
        return chatAuthenticationService.getAllUsers();
    }

    @GetMapping("/user/login/{login}")
    public ChatUser getUserByLogin(@PathVariable String login) {
        return chatAuthenticationService.getUserByLogin(login);
    }

    @GetMapping("/user/key/{key}")
    public ChatUser getUserByKey(@PathVariable String key) {
        return chatAuthenticationService.getUserByPublicKey(key);
    }

    @PostMapping("/users")
    public ChatUser create(@RequestBody ChatUser user) {
        return chatAuthenticationService.registerNewUser(user);
    }
}
