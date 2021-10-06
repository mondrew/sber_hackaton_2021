package com.example.dchat.service;

import com.example.dchat.model.ChatMessage;
import com.example.dchat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public void sendMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getMessages(String chatId) {
        return chatMessageRepository.findByChatId(chatId);
    }
}
