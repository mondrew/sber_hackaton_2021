package com.example.blockchain.service;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface CryptographyService {
    void initKeys();
    PrivateKey getPrivate(String filename) throws Exception;
    PublicKey getPublic(String filename) throws Exception;
    String encryptMsg(String msg, String recipientPublicKeyString) throws Exception;
    String decryptMsg(String msg) throws Exception;
    String signMsg(String msg) throws Exception;
    boolean verifyDigitalSignature(String msg, String senderPublicKeyString);
    String decryptDigitalSignature(String msg, String senderPublicKeyString);
}