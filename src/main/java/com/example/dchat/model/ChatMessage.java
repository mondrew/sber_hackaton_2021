package com.example.dchat.model;

import com.example.blockchain.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String sender;
    private String recipient;
    private String senderName;
    private String recipientName;
    private String message;
    private LocalDateTime timestamp;

    public ChatMessage(Transaction transaction) {
        this.sender = transaction.getSender();
        this.recipient = transaction.getRecipient();
        this.message = transaction.getMessage();
        //todo: add timestamp
        //todo: перенести в mapper
    }
}
