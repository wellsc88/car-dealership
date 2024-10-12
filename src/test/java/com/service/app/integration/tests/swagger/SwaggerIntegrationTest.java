package com.service.app.integration.tests.swagger;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.service.app.configuration.TestConfigs;
import com.service.app.integration.tests.testcontainers.AbstractIntegrationTest;

/**
 * Integration test for verifying that the Swagger UI page is correctly loaded in the web environment.
 *
 * This test starts the application on a defined port and ensures that the Swagger UI can be accessed
 * and displayed as expected.
 *
 * Annotations:
 * - @SpringBootTest: Loads the full Spring Boot application context with a defined web environment for testing.
 *
 * The test checks the Swagger UI page by sending a request to the Swagger URL and verifying the status code and content.
 *
 * @author Wellington
 * @version 1.0 
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("Test for Should Display Swagger UI Page")
    void testShouldDisplaySwaggerUiPage() {
        var content = given()
            .basePath("/swagger-ui/index.html")
            .port(TestConfigs.SERVER_PORT)
            .when()
                .get()
            .then()
                .statusCode(200)
            .extract()
                .body()
                    .asString();
        
        assertTrue(content.contains("Swagger UI"));
    }
}