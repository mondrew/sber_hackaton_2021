package com.example.dchat.model;

import com.example.blockchain.model.Transaction;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String senderPublicKey;
    private String recipientPublicKey;
    private String senderLogin;
    private String recipientLogin;
    private String message;
    private Timestamp timestamp;

    public ChatMessage(Transaction transaction) {
        this.senderPublicKey = transaction.getSenderPublicKey();
        this.recipientPublicKey = transaction.getRecipientPublicKey();
        this.message = new Gson().fromJson(transaction.getMessage(), TransactionMessage.class).getMessage();
        this.timestamp = transaction.getTimestamp();
        //todo: перенести в mapper
    }
}
