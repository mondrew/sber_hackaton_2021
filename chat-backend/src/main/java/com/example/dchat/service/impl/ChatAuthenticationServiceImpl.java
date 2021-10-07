package com.example.dchat.service.impl;

import com.example.dchat.model.ChatUser;
import com.example.dchat.repository.ChatUsersRepository;
import com.example.dchat.service.ChatAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatAuthenticationServiceImpl implements ChatAuthenticationService {

    private final ChatUsersRepository chatUsersRepository;

    public ChatUser registerNewUser(ChatUser user) {
        if (chatUsersRepository.findByLogin(user.getLogin()).isEmpty() &&
                chatUsersRepository.findByPublicKey(user.getPublicKey()).isEmpty()) {
            return chatUsersRepository.save(user);
        }
        return null;
    }

    public List<ChatUser> getAllUsers() {
        return chatUsersRepository.findAll();
    }

    public ChatUser getUserByLogin(String login) {
        return chatUsersRepository.findByLogin(login).orElse(null);
    }

    public ChatUser getUserByPublicKey(String publicKey) {
        return chatUsersRepository.findByPublicKey(publicKey).orElse(null);
    }
}
