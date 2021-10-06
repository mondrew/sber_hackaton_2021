package com.example.blockchain.service;

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

public interface KeysGeneratorService {

    public void createKeys();
    public PrivateKey getPrivateKey();
    public PublicKey getPublicKey();
    public void generateKeyPair();
    public boolean isKeysExists();
    public void updateKeyPair();
}
