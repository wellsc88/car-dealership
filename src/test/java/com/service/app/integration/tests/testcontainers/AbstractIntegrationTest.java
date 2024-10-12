package com.service.app.integration.tests.testcontainers;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

/**
 * Abstract base class for integration tests, responsible for setting up the test environment.
 *
 * This class utilizes Testcontainers to run a MySQL container for integration testing. It dynamically
 * configures the application's datasource by starting a MySQL container and injecting the database connection
 * details into the Spring environment.
 *
 * Annotations:
 * - @ContextConfiguration: Specifies the context configuration and an initializer class that customizes
 * the test environment with containerized MySQL settings.
 * 
 * @author Wellington
 * @version 1.0  
 *
 */
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

	/**
     * Inner class that initializes the test environment by starting the MySQL container and 
     * configuring the datasource properties dynamically.
     *
     * Implements the ApplicationContextInitializer to customize the Spring context before it is refreshed.
     *
     */
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    	// Define the MySQL container using Testcontainers.
        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.30");
    
       /**
        * Starts the required containers, ensuring that all necessary services are running for the tests.
        */
	    private static void startContainers() {
	        Startables.deepStart(Stream.of(mysql)).join();
	    }
	    
	   /**
        * Creates a map containing the datasource configuration details, such as URL, username, and password,
        * which are retrieved from the running MySQL container.
        *
        * @return Map<String, String> containing the datasource configuration.        
        */
	    private static Map<String, String> createConnectionConfiguration(){
	        return Map.of(
	            "spring.datasource.url", mysql.getJdbcUrl(),
	            "spring.datasource.username", mysql.getUsername(),
	            "spring.datasource.password", mysql.getPassword());
	    }
	    
	   /**
        * Initializes the Spring application context by starting the containers and setting the 
        * datasource configuration properties. The properties are injected into the environment
        * so that they are available for the Spring Boot application during testing.
        *
        * @param applicationContext the configurable application context for the test environment.
        */
	    @Override
	    @SuppressWarnings({ "rawtypes", "unchecked" })
	    public void initialize(ConfigurableApplicationContext applicationContext) {
	        startContainers();
	        ConfigurableEnvironment environment = applicationContext.getEnvironment();
	        MapPropertySource testcontaines =
	            new MapPropertySource(
	                "testcontainers",
	                (Map) createConnectionConfiguration());
	        
	        	environment.getPropertySources().addFirst(testcontaines);
    	 }    
    }
}
