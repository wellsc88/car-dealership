package com.service.app.configuration;

/**
 * Configuration class for setting up test constants.
 *
 * This class defines constants that are used across various tests, such as the server port 
 * and content type for HTTP requests.
 *
 * Constants:
 * - SERVER_PORT: Defines the port used for running tests that require server interaction.
 * - CONTENT_TYPE_JSON: Specifies the content type for JSON requests used in tests.
 * 
 * @author Wellington
 * @version 1.0 
 * 
 */
public class TestConfigs {
	
	 public static final int SERVER_PORT = 8088;
     public static final String CONTENT_TYPE_JSON = "application/json";

}
