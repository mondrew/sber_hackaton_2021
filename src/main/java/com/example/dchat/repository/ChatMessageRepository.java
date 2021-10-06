package com.example.dchat.repository;

import com.example.dchat.mapper.TransactionMessageMapper;
import com.example.dchat.model.ChatMessage;
import com.example.blockchain.model.Block;
import com.example.blockchain.model.Transaction;
import com.example.blockchain.service.BlockchainFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public void save(ChatMessage message) {
        blockchainFacade.addTransaction(transactionMessageMapper.chatMessageToTransaction(message));
    }

    private boolean isRequiredTransaction(String chatId, Transaction transaction) {
        String sender = transaction.getSender();
        String recipient = transaction.getRecipient();
        return chatId.equals(sender + "_" + recipient) || chatId.equals(recipient + "_" + sender);
    }
}
