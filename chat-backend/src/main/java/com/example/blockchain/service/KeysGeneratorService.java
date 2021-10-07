package com.example.blockchain.service;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeysGeneratorService {

    public void createKeys();
    public PrivateKey getPrivateKey();
    public PublicKey getPublicKey();
    public void generateKeyPair();
    public boolean isKeysExists();
    public void updateKeyPair();
}
