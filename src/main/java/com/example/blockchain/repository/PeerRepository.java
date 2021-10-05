package com.example.blockchain.repository;

import com.example.blockchain.model.Peer;

import java.util.List;

public interface PeerRepository {

    void savePeers(List<Peer> newPeers);
    void removePeer(Peer peer);
    List<Peer> getActivityPeers();
    boolean isContains(Peer peer);

}
