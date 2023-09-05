package dev.svero.playground.helloworld;

import dev.svero.playground.helloworld.utils.KeyStoreUtils;
import dev.svero.playground.helloworld.utils.SSLUtils;

import org.apache.commons.lang3.StringUtils;
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
	private static final String PROPERTY_CONFIGURATION = "configuration";
	private static final String ENVIRONMENT_CONFIGURATION = "VERUNA_CLIENT_CONFIG_FILE";

	public static void main(String... args) {
		try {
			String filename = getConfigurationFilename(args);
			LOGGER.debug("Reading settings from \"{}\"", filename);

			Configuration configuration = new Configuration();
			configuration.init(filename);

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

	/**
	 * Returns the name of the file that contains the configuration.
	 * 
	 * @param args String array with command-line arguments
	 * @return Filename
	 */
	private static String getConfigurationFilename(String... args) {
		String filename;
		if (args.length > 0 && StringUtils.isNotBlank(args[0])) {
			LOGGER.debug("Using specified filename");
			filename = args[0].trim();
		} else if (System.getProperties().containsKey(PROPERTY_CONFIGURATION)) {
			LOGGER.debug("Using information from system property");
			filename = System.getProperty(PROPERTY_CONFIGURATION);
		} else if (System.getenv(ENVIRONMENT_CONFIGURATION) != null) {
			LOGGER.debug("Using information from environment variable");
			filename = System.getenv(ENVIRONMENT_CONFIGURATION);
		} else {
			LOGGER.debug("Using default file name for configuration");
			filename = "application.properties";
		}

		return filename;
	}
}