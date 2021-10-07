package com.example.dchat.repository;

import com.example.blockchain.model.Transaction;
import com.example.blockchain.service.BlockchainFacade;
import com.example.dchat.model.ChatUser;
import com.example.dchat.model.TransactionMessage;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ChatUsersRepository {

    private final BlockchainFacade blockchainFacade;

    public List<ChatUser> findAll() {
        return blockchainFacade.getChain().getChain()
                .stream()
                .skip(1)
                .flatMap(block -> block.getTransactions().stream())
                .filter(transaction -> transaction.getRecipientPublicKey().equals("BLOCKCHAIN_SERVICE_REG_NEW_USER"))
                .filter(t -> new Gson().
                        fromJson(t.getMessage(), TransactionMessage.class)
                        .getType().equals("registration"))
                .map(ChatUser::createNewUser)
                .collect(Collectors.toList());
    }

    public Optional<ChatUser> findByLogin(String login) {
        return blockchainFacade.getChain().getChain()
                .stream()
                .skip(1)
                .flatMap(block -> block.getTransactions().stream())
                .filter(transaction -> transaction.getRecipientPublicKey().equals("BLOCKCHAIN_SERVICE_REG_NEW_USER"))
                .filter(t -> new Gson().
                        fromJson(t.getMessage(), TransactionMessage.class)
                        .getType().equals("registration"))
                .map(ChatUser::createNewUser)
                .filter(user -> user.getLogin().equals(login))
                .findAny();
    }

    public Optional<ChatUser> findByPublicKey(String publicKey) {
        return blockchainFacade.getChain().getChain()
                .stream()
                .skip(1)
                .flatMap(block -> block.getTransactions().stream())
                .filter(transaction -> transaction.getRecipientPublicKey().equals("BLOCKCHAIN_SERVICE_REG_NEW_USER"))
                .filter(t -> t.getSenderPublicKey().equals(publicKey))
                .map(ChatUser::createNewUser)
                .findAny();
    }

    public ChatUser save(ChatUser user) {
        String msg = new Gson().toJson(new TransactionMessage("registration", new Gson().toJson(user)));
        Transaction transaction = new Transaction(user.getPublicKey(), "BLOCKCHAIN_SERVICE_REG_NEW_USER",
                msg, 0.0, 0.0, new Timestamp(System.currentTimeMillis()));
        blockchainFacade.addTransaction(transaction);
        return user;
    }
}
