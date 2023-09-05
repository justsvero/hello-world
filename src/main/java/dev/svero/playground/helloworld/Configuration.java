package dev.svero.playground.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Implements a class for accessing the settings read from a
 * properties file.
 *
 * @author Sven Roeseler
 */
public class Configuration {
    final static Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
    final Properties properties = new Properties();

    /**
     * Tries to load the properties.
     */
    public boolean init(final String filename) {
        boolean result = true;

        InputStream inputStream;

        File file = new File(filename);
        if (file.exists() && file.isFile()) {
            // Try to load from normal file
            try {
                inputStream = new FileInputStream(filename);
            } catch (FileNotFoundException ex) {
                LOGGER.error("File not found: {}", filename);
                throw new IllegalStateException("Specified file was not found: " + filename, ex);
            }
        } else {
            // Try to load from resource
            if (filename.startsWith("/")) {
                LOGGER.debug("Try to load configuration as classpath resource: {}", 
                    filename);
                inputStream = getClass().getResourceAsStream(filename);
            } else {
                String tmpFilename = "/" + filename;
                LOGGER.debug("Try to load configuration as classpath resource: {}", 
                    tmpFilename);
                // We need to prefix the path with "/" to find in within the resource path
                inputStream = getClass().getResourceAsStream(tmpFilename);
            }

            if (inputStream == null) {
                LOGGER.warn("Properties file not found");
                return false;
            }
        }

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("An unspecified error occurred while reading the file", e);
            throw new IllegalStateException("Could not load data from file", e);
        }

        return result;
    }

    /**
     * Gets the filename of the key store.
     *
     * @return Key tore filename
     */
    public String getKeyStoreFilename() {
        return properties.getProperty("keystore.filename");
    }

    /**
     * Gets the password for accessing the key store.
     *
     * @return Key store password
     */
    public String getKeyStorePassword() {
        return properties.getProperty("keystore.password");
    }

    /**
     * Gets the filename of the trust store
     *
     * @return Trust store filename
     */
    public String getTrustStoreFilename() {
        return properties.getProperty("truststore.filename");
    }

    /**
     * Gets the password for accessing the trust store.
     *
     * @return Trust store password
     */
    public String getTrustStorePassword() {
        return properties.getProperty("truststore.password");
    }
}
