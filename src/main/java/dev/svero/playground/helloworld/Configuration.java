package dev.svero.playground.helloworld;

import dev.svero.playground.helloworld.exceptions.ConfigurationException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Implements a class for accessing the settings read from a
 * properties file.
 *
 * @author Sven Roeseler
 */
public class Configuration {
    private static final String DEFAULT_PROPERTIES_FILENAME = "/application.properties";
    private boolean initialized = false;

    final String propertiesFilename;
    final Properties properties = new Properties();

    /**
     * Creates a new instance using the default filename for
     * properties.
     */
    public Configuration() {
        propertiesFilename = DEFAULT_PROPERTIES_FILENAME;
    }

    /**
     * Creates a new instance using the specified filename.
     *
     * @param propertiesFilename Filename of the properties
     */
    public Configuration(final String propertiesFilename) {
        if (StringUtils.isBlank(propertiesFilename)) {
            throw new IllegalArgumentException("propertiesFilename may not be blank");
        }

        this.propertiesFilename = propertiesFilename;
    }

    /**
     * Tries to load the properties.
     */
    private void loadProperties() {
        if (!properties.isEmpty()) {
            return;
        }

        try {
            properties.load(this.getClass().getResourceAsStream(propertiesFilename));
            initialized = true;
        } catch (IOException ex) {
            System.err.println("An error occurred: " + ex.getMessage());
            throw new ConfigurationException("Could not load the properties", ex);
        }
    }

    /**
     * Gets the filename of the key store.
     *
     * @return Key tore filename
     */
    public String getKeyStoreFilename() {
        if (!initialized) {
            loadProperties();
        }

        return properties.getProperty("keystore.filename");
    }

    /**
     * Gets the password for accessing the key store.
     *
     * @return Key store password
     */
    public String getKeyStorePassword() {
        if (!initialized) {
            loadProperties();
        }

        return properties.getProperty("keystore.password");
    }

    /**
     * Gets the filename of the trust store
     *
     * @return Trust store filename
     */
    public String getTrustStoreFilename() {
        if (!initialized) {
            loadProperties();
        }

        return properties.getProperty("truststore.filename");
    }

    /**
     * Gets the password for accessing the trust store.
     *
     * @return Trust store password
     */
    public String getTrustStorePassword() {
        if (!initialized) {
            loadProperties();
        }

        return properties.getProperty("truststore.password");
    }
}