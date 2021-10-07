package com.example.dchat.model;

import com.example.blockchain.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String sender;
    private String recipient;
    private String senderLogin;
    private String recipientLogin;
    private String message;
    private Timestamp timestamp;

    public ChatMessage(Transaction transaction) {
        this.sender = transaction.getSenderPublicKey();
        this.recipient = transaction.getRecipientPublicKey();
        this.message = transaction.getMessage();
        this.timestamp = transaction.getTimestamp();
        //todo: перенести в mapper
    }
}
