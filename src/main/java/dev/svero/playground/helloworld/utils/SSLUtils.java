package dev.svero.playground.helloworld.utils;

import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.*;

/**
 * Implements methods for handling SSL/TLS.
 *
 * @author Sven Roeseler
 */
public class SSLUtils {
    /**
     * Creates a SSL context.
     *
     * @return SSL context or null
     */
    public SSLContext createSSLContext(KeyStore keyStore, String keyStorePassword, KeyStore trustStore) {
        if (keyStore == null) {
            throw new IllegalArgumentException("keyStore should not be null");
        }

        if (StringUtils.isBlank(keyStorePassword)) {
            throw new IllegalArgumentException("keyStorePassword should not be empty");
        }

        if (trustStore == null) {
            throw new IllegalArgumentException("trustStore should not be null");
        }

        SSLContext context = null;

        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("RSA");
            kmf.init(keyStore, keyStorePassword.toCharArray());

            context = SSLContext.getInstance("TLS");
            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create the SSL context because the " +
                    "required algorithm does not exist", e);
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException("Could not find a key in the specified key store", e);
        } catch (KeyStoreException e) {
            throw new RuntimeException("An unspecified exception occurred while accessing the keystore", e);
        } catch (KeyManagementException e) {
            throw new RuntimeException("An unspecified exception occurred while trying to get the key manager", e);
        }

        return context;
    }
}
