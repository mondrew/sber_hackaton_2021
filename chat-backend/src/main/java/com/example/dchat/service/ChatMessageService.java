package com.example.dchat.service;

import com.example.dchat.model.ChatMessage;

import java.util.List;

public interface ChatMessageService {

    void sendMessage(ChatMessage chatMessage);
    List<ChatMessage> getMessages(String chatId);
}
