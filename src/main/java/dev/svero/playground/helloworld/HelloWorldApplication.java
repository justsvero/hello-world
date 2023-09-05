package dev.svero.playground.helloworld;

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
	}
}
