package com.example.dchat.service;

import com.example.dchat.model.ChatMessage;

import java.util.List;

public interface ChatMessageService {

    ChatMessage sendMessage(ChatMessage chatMessage);
    List<ChatMessage> getMessages(String chatId);
    List<ChatMessage> getAllMessages();
}
