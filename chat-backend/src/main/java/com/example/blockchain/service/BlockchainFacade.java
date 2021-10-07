package com.example.blockchain.service;

import com.example.blockchain.dto.BlockDto;
import com.example.blockchain.dto.ChainDto;
import com.example.blockchain.dto.PeerListDto;
import com.example.blockchain.dto.PingDto;
import com.example.blockchain.dto.TransactionDto;
import com.example.blockchain.model.Transaction;

public interface BlockchainFacade {
    ChainDto getChain();
    void handleBlock(BlockDto blockDto);
    void addTransaction(Transaction transaction);
    void handleTransaction(TransactionDto transactionDto);
    void handlePing(PingDto pingDto);
    void handlePeerList(PeerListDto peerListDto);
    void resumeMining();
    void pauseMining();
}
