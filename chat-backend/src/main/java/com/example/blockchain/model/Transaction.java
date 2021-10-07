package com.example.blockchain.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private String senderPublicKey;
    private String recipientPublicKey;
    private String message;
    private Double amount;
    private Double fee;
    private Timestamp timestamp;
}
