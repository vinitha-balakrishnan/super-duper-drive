package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {


    @Autowired
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;


    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(Credential add,int userId) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String password = add.getPassword();
        String encyptPassword = this.encryptionService.encryptValue(password, encodedSalt);
        add.setKey(encodedSalt);
        add.setPassword(encyptPassword);
        add.setUserId(userId);
        return  credentialMapper.insertCredential(add);

    }
    public int updateCredential(Credential credential) {
            String key =credentialMapper.getKey(credential.getCredentialId());
            credential.setKey(key);
            credential.setPassword(encryptionService.encryptValue(credential.getPassword(),key));
        return this.credentialMapper.updateCredential(credential);
    }


    public void deleteCredential(int id) {
        credentialMapper.deleteCredential(id);
    }

    public List<Credential> getAllUserCredentials(int userId) {
        return credentialMapper.getAllCredentials(userId);
    }

    public int deletecredential(int credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }




}
