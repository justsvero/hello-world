package dev.svero.playground.helloworld.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Implements methods for handling key stores.
 *
 * @author Sven Roeseler
 */
public class KeyStoreUtils {
    /**
     * Tries to load a key store using the specified format.
     *
     * @param keyStoreFilename Filename of the key store
     * @param keyStorePassword Password for accessing the key store
     * @param keyStoreType Type (JKS, PKCS.12)
     * @return Created KeyStore instance
     */
    public KeyStore getKeyStore(final String keyStoreFilename, final String keyStorePassword, final String keyStoreType) {
        if (StringUtils.isBlank(keyStoreFilename)) {
            throw new IllegalArgumentException("keyStoreFilename should not be blank");
        }

        if (StringUtils.isBlank(keyStorePassword)) {
            throw new IllegalArgumentException("keyStorePassword should not be blank");
        }

        if (StringUtils.isBlank(keyStoreType)) {
            return getKeyStore(keyStoreFilename, keyStorePassword);
        }

        KeyStore keyStore = null;

        try {
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(new FileInputStream(keyStoreFilename), keyStorePassword.toCharArray());
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return keyStore;
    }

    /**
     * Tries to load a key store using the PKCS.12 format.
     *
     * @param keyStoreFilename Filename of the key store
     * @param keyStorePassword Password for accessing the key store
     * @return Created KeyStore instance
     */
    public KeyStore getKeyStore(final String keyStoreFilename, final String keyStorePassword) {
        return getKeyStore(keyStoreFilename, keyStorePassword, "PKCS12");
    }
}
