package org.nistagram.contentmicroservice.keystore;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.nistagram.contentmicroservice.data.model.IssuerData;
import org.nistagram.contentmicroservice.exceptions.KeystoreErrorException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class KeyStoreReader {

    private KeyStore keyStore;

    public KeyStoreReader() {
        try {
            keyStore = KeyStore.getInstance("PKCS12", "SUN");
        } catch (KeyStoreException | NoSuchProviderException e) {
            // TODO: log error
            throw new KeystoreErrorException();
        }
    }

    public IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, password);
            Certificate cert = keyStore.getCertificate(alias);

            PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, keyPass);
            X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
            return new IssuerData(privKey, issuerName);
        } catch (UnrecoverableKeyException | CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
            // TODO: log error
            throw new KeystoreErrorException();
        }
    }

    public Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, keyStorePass.toCharArray());

            if (keyStore.isKeyEntry(alias)) {
                return keyStore.getCertificate(alias);
            }
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            // TODO: log error
            throw new KeystoreErrorException();
        }
        return null;
    }

    public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, keyStorePass.toCharArray());

            if (keyStore.isKeyEntry(alias)) {
                return (PrivateKey) keyStore.getKey(alias, pass.toCharArray());
            }
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | UnrecoverableKeyException e) {
            // TODO: log error
            throw new KeystoreErrorException();
        }
        return null;
    }
}

