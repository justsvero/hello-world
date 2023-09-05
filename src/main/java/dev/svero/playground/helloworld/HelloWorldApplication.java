package dev.svero.playground.helloworld;

import dev.svero.playground.helloworld.exceptions.ConfigurationException;
import org.apache.commons.lang3.StringUtils;

/**
 * Implements the entry point for the application.
 *
 * @author Sven Roeseler
 */
public class HelloWorldApplication {
	public static void main(String... args) {
		String addressee = "World";

		if (args.length > 0 && StringUtils.isNotBlank(args[0])) {
			addressee = args[0];
		}
		
		System.out.println("Hello, " + addressee + "!");

		try {
			Configuration configuration = new Configuration();
			final String keystoreFilename = configuration.getKeystoreFilename();
			System.out.println("Keystore: " + keystoreFilename);
		} catch (ConfigurationException ex) {
			System.err.println("Error while accessing the configuration");
		}
	}
}
