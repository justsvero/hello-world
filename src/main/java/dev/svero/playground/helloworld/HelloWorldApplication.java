package dev.svero.playground.helloworld;

import dev.svero.playground.helloworld.utils.KeyStoreUtils;
import dev.svero.playground.helloworld.utils.SSLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

/**
 * Implements the entry point for the application.
 *
 * @author Sven Roeseler
 */
public class HelloWorldApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldApplication.class);

	public static void main(String... args) {
		try {
			Configuration configuration = new Configuration();
			KeyStoreUtils keyStoreUtils = new KeyStoreUtils();
			SSLUtils sslUtils = new SSLUtils();

			final String keystoreFilename = configuration.getKeyStoreFilename();
			final String keyStorePassword = configuration.getKeyStorePassword();
			KeyStore keyStore = keyStoreUtils.loadKeyStore(keystoreFilename, keyStorePassword);

			final String trustStoreFilename = configuration.getTrustStoreFilename();
			final String trustStorePassword = configuration.getTrustStorePassword();
			KeyStore trustStore = keyStoreUtils.loadKeyStore(trustStoreFilename, trustStorePassword);

			SSLContext sslContext = sslUtils.createSSLContext(keyStore, keyStorePassword, trustStore);
		} catch (Exception ex) {
			LOGGER.error("An error occurred", ex);
		}
	}
}
