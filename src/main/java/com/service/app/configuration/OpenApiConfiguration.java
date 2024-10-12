package com.service.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

/**
 * Configures OpenAPI documentation for the Car Dealership API.
 * 
 * <p>
 * This class provides metadata and configuration for generating
 * API documentation using OpenAPI (Swagger).
 * </p>
 * 
 * @author Wellington
 * @version 1.0
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "Car Dealership API", version = "v1", description = "Documentation of Car Dealership API"))
public class OpenApiConfiguration {

    /**
     * Creates and configures an {@link OpenAPI} instance.
     * 
     * @return a configured {@link OpenAPI} instance with basic API information.
     */
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Car Dealership API")
                        .version("v1")
                        .description("Car Dealership API service.")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
