package com.example.dchat.service;

import com.example.dchat.model.ChatUser;

import java.util.List;

public interface ChatAuthenticationService {

    ChatUser registerNewUser(ChatUser user);
    List<ChatUser> getAllUsers();
    ChatUser getUserByLogin(String login);
    ChatUser getUserByPublicKey(String login);
}
