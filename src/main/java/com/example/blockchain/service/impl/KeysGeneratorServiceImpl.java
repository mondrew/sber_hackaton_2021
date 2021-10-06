package com.example.blockchain.service.impl;

import com.example.blockchain.service.KeysGeneratorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class KeysGeneratorServiceImpl implements KeysGeneratorService {

    @Value("${cryptography.key.path}")
    private String KEYS_DIR;
    @Value("${cryptography.key.public.file}")
    private String PUBLIC_KEY_FILE;
    @Value("${cryptography.key.private.file}")
    private String PRIVATE_KEY_FILE;
    private static final int KEY_LENGTH = 2048;
    private final KeyPairGenerator keyGen;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeysGeneratorServiceImpl() throws NoSuchAlgorithmException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(KEY_LENGTH);
    }

    public void createKeys() {
        KeyPair pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    private void writeToFile(String path, byte[] key, boolean isPrivate) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(key);
            fos.flush();
        }
        f.setExecutable(false);
        f.setWritable(false);
        if (isPrivate) {
            f.setReadable(true, true);
        }
    }

    public void generateKeyPair() {
        try {
            createKeys();
            writeToFile(KEYS_DIR + PUBLIC_KEY_FILE, getPublicKey().getEncoded(), false);
            writeToFile(KEYS_DIR + PRIVATE_KEY_FILE, getPrivateKey().getEncoded(), true);
            log.info("RSA keys generated");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean isKeysExists() {
        File[] files = new File(KEYS_DIR).listFiles();
        if (files == null) {
            return false;
        }
        List<String> keyNames = Arrays.stream(files).map(File::getName).collect(Collectors.toList());
        return (keyNames.containsAll(List.of(PUBLIC_KEY_FILE, PRIVATE_KEY_FILE)));
    }

    public void updateKeyPair() {
        File sshDir = new File(KEYS_DIR);

        for (File f : sshDir.listFiles()) {
            f.delete();
        }
        try {
            createKeys();
            writeToFile(KEYS_DIR + PUBLIC_KEY_FILE, getPublicKey().getEncoded(), false);
            writeToFile(KEYS_DIR + PRIVATE_KEY_FILE, getPrivateKey().getEncoded(), true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
