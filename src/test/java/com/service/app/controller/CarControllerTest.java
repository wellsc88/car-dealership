package com.service.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.app.entity.Car;
import com.service.app.exceptions.ResourceNotFoundException;
import com.service.app.service.CarService;

/**
 * Unit tests for the CarController class.
 *
 * This test class is annotated with @WebMvcTest, which is used to test only the web layer 
 * (specifically the controller layer) of the Spring Boot application.
 * 
 * It automatically configures the Spring MVC infrastructure and allows testing the behavior
 * of the CarController in isolation from other parts of the application like services or data layers.
 *
 * Mock dependencies such as services can be injected to focus only on testing the controller's endpoints.
 *
 * @author Wellington
 * @version 1.0  
 * 
 */
@WebMvcTest
class CarControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;
    
    @MockBean
    private CarService service;
    
    private Car car;

	@BeforeEach
	void setUp() {
		
		// Given / Arrange				
		car = new Car(1L, "Volkswagen", "Polo MPI", 2024, "Vermelha", "BGA7230", 0, "Total Flex", "Automática de 6 velocidades", 116, new BigDecimal(1.0), 4,
				"Hatchback", new BigDecimal(93500.0), LocalDate.of(2024, 9, 20), "Volkswagen do Brasil");

	}
	
	@Test
    @DisplayName("Test for Given Car Object when Create Car then Return Saved Car")
    void testGivenCarObject_WhenCreateCar_thenReturnSavedCar() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        given(service.createCar(any(Car.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));
        
        // When / Act
        ResultActions response = mockMvc.perform(post("/car-service")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(car)));
        
        // Then / Assert
        response.andDo(print()).
            andExpect(status().isOk())
            .andExpect(jsonPath("$.color", is(car.getColor())))
            .andExpect(jsonPath("$.licensePlate", is(car.getLicensePlate())))
            .andExpect(jsonPath("$.fuelType", is(car.getFuelType())));
    }
	    
    @Test
    @DisplayName("Test for Given List of Cars when findAll Cars then Return Cars List")
    void testGivenListOfCars_WhenFindAllCars_thenReturnCarsList() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        List<Car> cars = new ArrayList<>();
        cars.add(car);
              
    	cars.add(new Car(2L, "Volkswagen", "Amarok V6 Comfortline", 2024, "Azul Marinho", "CMB7761", 0, "Diesel", "Automática de 8 velocidades", 258, new BigDecimal(3.0), 4,
				"Picape", new BigDecimal(280000.0), LocalDate.of(2024, 9, 24), "Volkswagen do Brasil"));    	   
        
        given(service.findAllCars()).willReturn(cars);
        
        // When / Act
        ResultActions response = mockMvc.perform(get("/car-service"));
        
        // Then / Assert
        response.
            andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.size()", is(cars.size())));
    }
    
    @Test
    @DisplayName("Test for Given carId when findById then Return Car Object")
    void testGivenCarId_WhenFindById_thenReturnCarObject() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long carId = 1L;
        given(service.getCarById(carId)).willReturn(car);
        
        // When / Act
        ResultActions response = mockMvc.perform(get("/car-service/{id}", carId));
        
        // Then / Assert
        response.
            andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.color", is(car.getColor())))
            .andExpect(jsonPath("$.licensePlate", is(car.getLicensePlate())))
            .andExpect(jsonPath("$.fuelType", is(car.getFuelType())));
    }
    
    @Test
    @DisplayName("Test for Given Invalid CarId when findById then Return Not Found")
    void testGivenInvalidCarId_WhenFindById_thenReturnNotFound() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long carId = 1L;
        given(service.getCarById(carId)).willThrow(ResourceNotFoundException.class);
        
        // When / Act
        ResultActions response = mockMvc.perform(get("/car-service/{id}", carId));
        
        // Then / Assert
        response.andExpect(status().isNotFound())
            .andDo(print());
    }
    
    @Test
    @DisplayName("Test for Given Updated Car when Update then Return Updated Car Object")
    void testGivenUpdatedCar_WhenUpdate_thenReturnUpdatedCarObject() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long carId = 1L;
        given(service.getCarById(carId)).willReturn(car);
        given(service.updateCar(any(Car.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));
        
        // When / Act       
        Car updatedCar = new Car(1L, "Volkswagen", "Polo MPI", 2024, "Azul Prateado", "BGA7230", 0, "Total Flex", "Automática de 5 velocidades", 122, new BigDecimal(1.0), 4,
				"Hatchback", new BigDecimal(98000.0), LocalDate.of(2024, 9, 18), "Volkswagen do Brasil");
        
        ResultActions response = mockMvc.perform(put("/car-service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedCar)));
        
        // Then / Assert
        response.
            andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.color", is(updatedCar.getColor())))
            .andExpect(jsonPath("$.licensePlate", is(updatedCar.getLicensePlate())))
            .andExpect(jsonPath("$.fuelType", is(updatedCar.getFuelType())));
    }
    
    @Test
    @DisplayName("Test for Given Unexistent Car when Update then Return Not Found")
    void testGivenUnexistentCar_WhenUpdate_thenReturnNotFound() throws JsonProcessingException, Exception {
        
        // Given / Arrange      
        given(service.updateCar(any(Car.class))).willThrow(ResourceNotFoundException.class);
            
        // When / Act     
        Car updatedCar = new Car(1L, "Volkswagen", "Polo MPI", 2024, "Azul Prateado", "BGA7230", 0, "Total Flex", "Automática de 5 velocidades", 122, new BigDecimal(1.0), 4,
                "Hatchback", new BigDecimal(98000.0), LocalDate.of(2024, 9, 18), "Volkswagen do Brasil");
         
        ResultActions response = mockMvc.perform(put("/car-service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedCar)));
        
        // Then / Assert
        response.
            andExpect(status().isNotFound())
            .andDo(print());
    }
    
    @Test
    @DisplayName("Test for Given carId when Delete then Return NotContent")
    void testGivenCarId_WhenDelete_thenReturnNotContent() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long carId = 1L;
        willDoNothing().given(service).deleteCarById(carId);
        
        // When / Act
        ResultActions response = mockMvc.perform(delete("/car-service/{id}", carId));
        
        // Then / Assert
        response.
            andExpect(status().isNoContent())
                .andDo(print());
    }	

}
