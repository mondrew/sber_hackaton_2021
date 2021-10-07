package com.example.blockchain.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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