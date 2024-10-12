package com.service.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.service.app.entity.Car;
import com.service.app.repository.CarRepository;

/**
 * Unit tests for the CarService class.
 *
 * This test class uses the Mockito framework to mock dependencies
 * and verify the behavior of the CarService methods in isolation.
 *
 * The @ExtendWith(MockitoExtension.class) annotation ensures that Mockito's
 * annotations like @Mock and @InjectMocks are properly initialized before each test.
 * 
 * @author Wellington
 * @version 1.0 
 * 
 */
@ExtendWith(MockitoExtension.class)
class CarServiceTest {
	
	@Mock
    private CarRepository repository;
	    
	@InjectMocks
	private CarService services;
	
	private Car car;

	@BeforeEach
	void setUp() throws Exception {
		
		// Given / Arrange				
		car = new Car(1L, "Volkswagen", "Polo MPI", 2024, "Vermelha", "BGA7230", 0, "Total Flex", "Automática de 6 velocidades", 116, new BigDecimal(1.0), 4,
				"Hatchback", new BigDecimal(93500.0), LocalDate.of(2024, 9, 20), "Volkswagen do Brasil");

	}
	
	@DisplayName("Test for Given Car Object when Save Car then Return Car Object")
    @Test
    void testGivenCarObject_WhenSaveCar_thenReturnCarObject() {
        
        // Given / Arrange    
        given(repository.save(car)).willReturn(car);
        
        // When / Act
        Car savedCar = services.createCar(car);
        
        // Then / Assert
        assertNotNull(savedCar);
        assertEquals("BGA7230", savedCar.getLicensePlate());
    }   
           
    @DisplayName("Test for Given Cars List when findAll Cars then Return Cars List")
    @Test
    void testGivenCarsList_WhenFindAllCars_thenReturnCarsList() {
        
    	 // Given / Arrange   
    	Car car1 = new Car(2L, "Volkswagen", "Amarok V6 Comfortline", 2024, "Azul Marinho", "CMB7761", 0, "Diesel", "Automática de 8 velocidades", 258, new BigDecimal(3.0), 4,
				"Picape", new BigDecimal(280000.0), LocalDate.of(2024, 9, 24), "Volkswagen do Brasil");
    	        
        given(repository.findAll()).willReturn(List.of(car, car1));
        
        // When / Act
        List<Car> personsList = services.findAllCars();
        
        // Then / Assert
        assertNotNull(personsList);
        assertEquals(2, personsList.size());
    }   
    
    @DisplayName("Test for Given Empty Cars List when findAll Cars then Return Empty Cars List")
    @Test
    void testGivenEmptyCarsList_WhenFindAllCars_thenReturnEmptyCarsList() {
        
        // Given / Arrange
        given(repository.findAll()).willReturn(Collections.emptyList());
        
        // When / Act
        List<Car> personsList = services.findAllCars();
        
        // Then / Assert
        assertTrue(personsList.isEmpty());
        assertEquals(0, personsList.size());
    }   
    
    @DisplayName("Test for Given CarId when findById then Return Car Object")
    @Test
    void testGivenCarId_WhenFindById_thenReturnCarObject() {
        
        // Given / Arrange
        given(repository.findById(anyLong())).willReturn(Optional.of(car));
        
        // When / Act
        Car savedCar = services.getCarById(1L);
        
        // Then / Assert
        assertNotNull(savedCar);
        assertEquals("BGA7230", savedCar.getLicensePlate());
    }  
    
    @DisplayName("Test for Given Car Object when Update Car then Return Updated Car Object")
    @Test
    void testGivenCarObject_WhenUpdateCar_thenReturnUpdatedCarObject() {
        
        // Given / Arrange
        car.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(car));
        
        car.setColor("Amarela");
        car.setHorsePower(187);
        
        given(repository.save(car)).willReturn(car);
        
        // When / Act
        Car updatedCar = services.updateCar(car);
        
        // Then / Assert
        assertNotNull(updatedCar);
        assertEquals("Amarela", updatedCar.getColor());
        assertEquals(187, updatedCar.getHorsePower());
    }  
    
    @DisplayName("Test for Given CarID when Delete Car then do Nothing")
    @Test
    void testGivenCarID_WhenDeleteCar_thenDoNothing() {
        
        // Given / Arrange
        car.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(car));
        willDoNothing().given(repository).delete(car);
        
        // When / Act
        services.deleteCarById(car.getId());
        
        // Then / Assert
        verify(repository, times(1)).delete(car);
    }  	

}
