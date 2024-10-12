package com.service.app.integration.tests.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.service.app.configuration.TestConfigs;
import com.service.app.entity.Car;
import com.service.app.integration.tests.testcontainers.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

/**
 * Integration test for the CarController, ensuring that the controller's endpoints function as expected
 * within a full Spring Boot context.
 *
 * This test uses an actual web environment and defined port for testing REST API endpoints of the CarController.
 * The test order is controlled using the `OrderAnnotation` to ensure a specific sequence of execution.
 *
 * Annotations:
 * - @TestMethodOrder: Specifies the order in which test methods are executed.
 * - @SpringBootTest: Loads the full Spring Boot application context, with a defined port for testing.
 *
 * @author Wellington
 * @version 1.0  
 * 
 */
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CarControllerIntegrationTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Car car;
    
    @BeforeAll
    public static void setUp() {
        
        // Given / Arrange
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        specification = new RequestSpecBuilder()
            .setBasePath("/car-service")
            .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();        
   			
        car = new Car(1L, "Volkswagen", "Polo MPI", 2024, "Vermelha", "BGA7230", 0, "Total Flex", "Automática de 6 velocidades", 116, new BigDecimal(1.0), 4,
     				"Hatchback", new BigDecimal(93500.0), LocalDate.of(2024, 9, 20), "Volkswagen do Brasil");
    }
    
    @Test
    @Order(1)
    @DisplayName("Integration Test given Car Object when Create one Car should Return a Car Object")
    void integrationTestGivenCarObject_when_CreateOneCar_ShouldReturnACarObject() throws JsonMappingException, JsonProcessingException {
        
    	  var content = given().spec(specification)  
    			    .contentType(TestConfigs.CONTENT_TYPE_JSON)
    			    .body(car)
    	            .when()
    	                .post()
    	            .then()
    	                .statusCode(200)
    	            .extract()
    	                .body()
    	                    .asString();
        
        Car createdCar = objectMapper.readValue(content, Car.class);
        
        car = createdCar;
        
        assertNotNull(createdCar);
        
        assertNotNull(createdCar.getId());
        assertNotNull(createdCar.getBrand());
        assertNotNull(createdCar.getModel());
        assertNotNull(createdCar.getModelYear());
        assertNotNull(createdCar.getColor());
        assertNotNull(createdCar.getFuelType());
        
        assertTrue(createdCar.getId() > 0);
        assertEquals("Volkswagen", createdCar.getBrand());
        assertEquals("Polo MPI", createdCar.getModel());
        assertEquals(2024, createdCar.getModelYear());
        assertEquals("Vermelha", createdCar.getColor());
        assertEquals("Total Flex", createdCar.getFuelType());
    }
    
    @Test
    @Order(2)
    @DisplayName("Integration Test given Car Object when Update one Car should Return an Updated Car Object")
    void integrationTestGivenCarObject_when_UpdateOneCar_ShouldReturnAnUpdatedCarObject() throws JsonMappingException, JsonProcessingException {
          
    	 car.setModel("Gol");
         car.setFuelType("Gasolina");
    	
    	  var content = given().spec(specification)  
    			    .contentType(TestConfigs.CONTENT_TYPE_JSON)
    			    .body(car)
    	            .when()
    	                .put()
    	            .then()
    	                .statusCode(200)
    	            .extract()
    	                .body()
    	                    .asString();
        
        Car updatedCar = objectMapper.readValue(content, Car.class);
        
        car = updatedCar;
        
        assertNotNull(updatedCar);
        
        assertNotNull(updatedCar.getId());
        assertNotNull(updatedCar.getBrand());
        assertNotNull(updatedCar.getModel());
        assertNotNull(updatedCar.getModelYear());
        assertNotNull(updatedCar.getColor());
        assertNotNull(updatedCar.getFuelType());
        
        assertTrue(updatedCar.getId() > 0);
        assertEquals("Volkswagen", updatedCar.getBrand());
        assertEquals("Gol", updatedCar.getModel());
        assertEquals(2024, updatedCar.getModelYear());
        assertEquals("Vermelha", updatedCar.getColor());
        assertEquals("Gasolina", updatedCar.getFuelType());
    }
    
    @Test
    @Order(3)
    @DisplayName("Integration Test given Car Object when findById should Return a Car Object")
    void integrationTestGivenCarObject_when_findById_ShouldReturnACarObject() throws JsonMappingException, JsonProcessingException {
        
        var content = given().spec(specification)
                .pathParam("id", car.getId())
            .when()
                .get("{id}")
            .then()
                .statusCode(200)
                    .extract()
                        .body()
                            .asString();
        
        Car foundCar = objectMapper.readValue(content, Car.class);
        
        assertNotNull(foundCar);
        
        assertNotNull(foundCar.getId());
        assertNotNull(foundCar.getBrand());
        assertNotNull(foundCar.getModel());
        assertNotNull(foundCar.getModelYear());
        assertNotNull(foundCar.getColor());
        assertNotNull(foundCar.getFuelType());
        
        assertTrue(foundCar.getId() > 0);
        assertEquals("Volkswagen", foundCar.getBrand());
        assertEquals("Gol", foundCar.getModel());
        assertEquals(2024, foundCar.getModelYear());
        assertEquals("Vermelha", foundCar.getColor());
        assertEquals("Gasolina", foundCar.getFuelType());
    }
    
    @Test
    @Order(4)
    @DisplayName("Integration Test given Car Object when findAll should Return a Cars List")
    void integrationTest_when_findAll_ShouldReturnACarsList() throws JsonMappingException, JsonProcessingException {
        
    	Car anotherCar = new Car(2L, "Volkswagen", "Amarok V6 Comfortline", 2024, "Azul Marinho", "CMB7761", 0, "Diesel", "Automática de 8 velocidades", 258, new BigDecimal(3.0), 4,
				"Picape", new BigDecimal(280000.0), LocalDate.of(2024, 9, 24), "Volkswagen do Brasil");    	   
              
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(anotherCar)
        .when()
            .post()
        .then()
            .statusCode(200);
        
        var content = given().spec(specification)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();
        
        Car[] myArray = objectMapper.readValue(content, Car[].class);
        List<Car> cars = Arrays.asList(myArray);
        
        Car foundCarOne = cars.get(0);
        
        assertNotNull(foundCarOne);
        
        assertNotNull(foundCarOne.getId());
        assertNotNull(foundCarOne.getBrand());
        assertNotNull(foundCarOne.getModel());
        assertNotNull(foundCarOne.getModelYear());
        assertNotNull(foundCarOne.getColor());
        assertNotNull(foundCarOne.getFuelType());
        
        assertTrue(foundCarOne.getId() > 0);
        assertEquals("Volkswagen", foundCarOne.getBrand());
        assertEquals("Gol", foundCarOne.getModel());
        assertEquals(2024, foundCarOne.getModelYear());
        assertEquals("Vermelha", foundCarOne.getColor());
        assertEquals("Gasolina", foundCarOne.getFuelType());
        
        Car foundCarTwo = cars.get(1);
        
        assertNotNull(foundCarTwo);
        
        assertNotNull(foundCarTwo.getId());
        assertNotNull(foundCarTwo.getBrand());
        assertNotNull(foundCarTwo.getModel());
        assertNotNull(foundCarTwo.getModelYear());
        assertNotNull(foundCarTwo.getColor());
        assertNotNull(foundCarTwo.getFuelType());

        assertTrue(foundCarTwo.getId() > 0);
        assertEquals("Volkswagen", foundCarTwo.getBrand());
        assertEquals("Amarok V6 Comfortline", foundCarTwo.getModel());
        assertEquals(2024, foundCarTwo.getModelYear());
        assertEquals("Azul Marinho", foundCarTwo.getColor());
        assertEquals("Diesel", foundCarTwo.getFuelType());
    }

    @Test
    @Order(5)
    @DisplayName("Integration Test given Car Object when delete should Return No Content")
    void integrationTestGivenCarObject_when_delete_ShouldReturnNoContent() throws JsonMappingException, JsonProcessingException {
        
        given().spec(specification)
                .pathParam("id", car.getId())
            .when()
                .delete("{id}")
            .then()
                .statusCode(204);
    }

}
