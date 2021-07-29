package com.callforcode.greenfarm.fabric;

import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.identity.X509Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoPrimitives;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Set;

@Slf4j
public class LocalUser implements User {

    private String name;

    private String mspId;

    private Enrollment enrollment;

    public LocalUser(String name, String mspId) {
        this.name = name;
        this.mspId = mspId;
    }

    public LocalUser(String name, String mspId, String keyFile, String certFile) {
        this.name = name;
        this.mspId = mspId;
        try {
            this.enrollment = loadFromPemFile(keyFile, certFile);
        } catch (Exception e) {
            log.error("LoadFromPemFile error", e);
        }
    }

    private Enrollment loadFromPemFile(String keyFile, String certFile) throws CryptoException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        byte[] keyPem = keyFile.getBytes();
        byte[] certPem = certFile.getBytes();
        CryptoPrimitives suite = new CryptoPrimitives();
        PrivateKey privateKey = suite.bytesToPrivateKey(keyPem);
        return new X509Enrollment(privateKey, new String(certPem));
    }

    public String getName() {
        return this.name;
    }

    public Set<String> getRoles() {
        return null;
    }

    public String getAccount() {
        return null;
    }

    public String getAffiliation() {
        return null;
    }

    public Enrollment getEnrollment() {
        return this.enrollment;
    }

    public String getMspId() {
        return this.mspId;
    }

}
