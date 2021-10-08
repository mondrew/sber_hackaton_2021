package com.example.dchat.repository;

import com.example.dchat.mapper.TransactionMessageMapper;
import com.example.dchat.model.ChatMessage;
import com.example.blockchain.model.Block;
import com.example.blockchain.model.Transaction;
import com.example.blockchain.service.BlockchainFacade;
import com.example.dchat.model.TransactionMessage;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {
    private final BlockchainFacade blockchainFacade;
    private final TransactionMessageMapper transactionMessageMapper;

    public List<ChatMessage> findByChatId(String chatId) {
        List<Block> chain = blockchainFacade.getChain().getChain();
        return chain
                .stream()
                .skip(1)
                .flatMap(block -> block.getTransactions().stream())
                .filter(transaction -> isRequiredTransaction(chatId, transaction))
                .map(ChatMessage::new)
                .collect(Collectors.toList());
    }

    public ChatMessage save(ChatMessage message) {
        String transactionMessage = new Gson().toJson(new TransactionMessage("message", message.getMessage()));
        blockchainFacade.addTransaction(transactionMessageMapper.chatMessageToTransaction(message, transactionMessage));
        return message;
    }

    public List<ChatMessage> getAllMessages() {
        return blockchainFacade.getChain().getChain()
                .stream()
                .skip(1)
                .flatMap(block -> block.getTransactions().stream())
                .filter(transaction -> !Objects.equals(transaction.getSenderPublicKey(), "BLOCK_CHAIN_BANK"))
                .filter(transaction -> !Objects.equals(transaction.getSenderPublicKey(), "BLOCKCHAIN_SERVICE_REG_NEW_USER"))
                .filter(t -> new Gson().
                        fromJson(t.getMessage(), TransactionMessage.class)
                        .getType().equals("message"))
                .map(ChatMessage::new)
                .collect(Collectors.toList());
    }

    private boolean isRequiredTransaction(String chatId, Transaction transaction) {
        String sender = transaction.getSenderPublicKey();
        String recipient = transaction.getRecipientPublicKey();
        return chatId.equals(sender + "_" + recipient) || chatId.equals(recipient + "_" + sender);
    }
}
