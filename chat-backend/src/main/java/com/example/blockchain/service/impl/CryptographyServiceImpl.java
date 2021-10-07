package com.example.blockchain.service.impl;

import com.example.blockchain.exceptions.DecryptionException;
import com.example.blockchain.exceptions.InvalidCryptographyKeyException;
import com.example.blockchain.service.CryptographyService;
import com.example.blockchain.service.KeysGeneratorService;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CryptographyServiceImpl implements CryptographyService {

    @Value("${cryptography.key.path}" + "${cryptography.key.private.file}")
    private String PATH_TO_PRIVATE_KEY;
    @Value("${cryptography.key.path}" + "${cryptography.key.public.file}")
    private String PATH_TO_PUBLIC_KEY;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    private final KeysGeneratorService keysGeneratorService;

    @PostConstruct
    public void initKeys() {
        if (keysGeneratorService.isKeysExists()) {
            return;
        }
        try {
            this.privateKey = getPrivate(PATH_TO_PRIVATE_KEY);
            this.publicKey = getPublic(PATH_TO_PUBLIC_KEY);
            log.info("RSA keys are found");
        } catch (Exception e) {
            keysGeneratorService.generateKeyPair();
        }
    }

    public PrivateKey getPrivate(String filename) throws InvalidCryptographyKeyException {
        try {
            byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
            EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new InvalidCryptographyKeyException("Invalid cryptography key");
        }
    }

    public PublicKey getPublic(String filename) throws InvalidCryptographyKeyException {
        try {
            byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new InvalidCryptographyKeyException("Invalid cryptography key");
        }
    }

    private static String getStringKeyFromFile(String filepath) throws IOException {
        byte[] keyBytes = Files.readAllBytes(new File(filepath).toPath());
        return java.util.Base64.getEncoder().encodeToString(keyBytes);
    }

    private PublicKey stringToPublicKey(String msg) throws InvalidCryptographyKeyException {
        try {
            byte[] keyBytes = java.util.Base64.getDecoder().decode(msg);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new InvalidCryptographyKeyException("Invalid cryptography key");
        }
    }

    private PrivateKey stringToPrivateKey(String msg) throws InvalidCryptographyKeyException {
        try {
            byte[] keyBytes = java.util.Base64.getDecoder().decode(msg);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        }
        catch (Exception e) {
            throw new InvalidCryptographyKeyException("Invalid cryptography key");
        }
    }

    @SneakyThrows
    public String encryptMsg(String msg, String recipientPublicKeyString) {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        PublicKey recipientPublicKey = stringToPublicKey(recipientPublicKeyString);
        encryptCipher.init(Cipher.ENCRYPT_MODE, recipientPublicKey);
        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMsgBytes = encryptCipher.doFinal(msgBytes);
        return java.util.Base64.getEncoder().encodeToString(encryptedMsgBytes);
    }

    @SneakyThrows
    public String decryptMsg(String msg) throws DecryptionException {
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        byte[] encryptedMsgBytes = java.util.Base64.getDecoder().decode(msg);
        try {
            byte[] decryptedMsgBytes = decryptCipher.doFinal(encryptedMsgBytes);
            return new String(decryptedMsgBytes, StandardCharsets.UTF_8);
        }
        catch (IllegalBlockSizeException | BadPaddingException e) {
            log.info("Message decryption failed");
            throw new DecryptionException("Message decryption failed: wrong key");
        }
    }

    @SneakyThrows
    public String signMsg(String msg) {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, this.privateKey);
        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
        byte[] signedMsgBytes = encryptCipher.doFinal(msgBytes);
        return java.util.Base64.getEncoder().encodeToString(signedMsgBytes);
    }

    @SneakyThrows
    private byte[] getEncryptedMsgBytes(String msg, String senderPublicKeyString, Cipher decryptCipher) {
        PublicKey senderPublicKey = stringToPublicKey(senderPublicKeyString);
        decryptCipher.init(Cipher.DECRYPT_MODE, senderPublicKey);
        return java.util.Base64.getDecoder().decode(msg);
    }

    @SneakyThrows
    public boolean verifyDigitalSignature(String msg, String senderPublicKeyString) {
        try {
            Cipher decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.doFinal(getEncryptedMsgBytes(msg, senderPublicKeyString, decryptCipher));
            return true;
        }
        catch (IllegalBlockSizeException | BadPaddingException e) {
            log.info("Digital signature is not valid");
            return false;
        }
    }

    @SneakyThrows
    public String decryptDigitalSignature(String msg, String senderPublicKeyString) throws DecryptionException {
        try {
            Cipher decryptCipher = Cipher.getInstance("RSA");
            byte[] decryptedMsgBytes = decryptCipher.doFinal(getEncryptedMsgBytes(msg,
                    senderPublicKeyString, decryptCipher));
            return new String(decryptedMsgBytes, StandardCharsets.UTF_8);
        }
        catch (IllegalBlockSizeException | BadPaddingException e) {
            log.info("Digital signature is not valid");
            throw new DecryptionException("Message decryption failed: wrong key");
        }
    }
}