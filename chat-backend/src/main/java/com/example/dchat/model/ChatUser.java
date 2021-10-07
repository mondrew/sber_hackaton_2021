package com.example.dchat.model;

import com.example.blockchain.model.Transaction;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatUser {

    private String publicKey;
    private String login;
    private String phoneNumber;

    public static ChatUser createNewUser(Transaction transaction) {
        Gson gson = new Gson();
        TransactionMessage transactionMessage = gson.fromJson(transaction.getMessage(), TransactionMessage.class);
        return gson.fromJson(transactionMessage.getMessage(), ChatUser.class);
    }
}
