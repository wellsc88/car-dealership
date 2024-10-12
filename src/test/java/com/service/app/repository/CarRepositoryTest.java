package com.service.app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.service.app.entity.Car;
import com.service.app.integration.tests.testcontainers.AbstractIntegrationTest;

/**
 * Unit tests for the CarRepository class.
 *
 * This test class uses the @DataJpaTest annotation to test JPA repositories in an in-memory database
 * or an external database, depending on the configuration.
 *
 * The @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) annotation ensures that the
 * test uses the configured external database instead of replacing it with an in-memory database.
 *
 * This class extends AbstractIntegrationTest to inherit common test configurations and behaviors.
 * 
 * @author Wellington
 * @version 1.0 
 *
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRepositoryTest extends AbstractIntegrationTest {
	
	@Autowired
	private CarRepository repository;
	
	private Car car;

	@BeforeEach
	void setUp(){
		
		// Given / Arrange					
		car = new Car(1L, "Volkswagen", "Polo MPI", 2024, "Vermelha", "BGA7230", 0, "Total Flex", "Automática de 6 velocidades", 116, new BigDecimal(1.0), 4,
				"Hatchback", new BigDecimal(93500.0), LocalDate.of(2024, 9, 20), "Volkswagen do Brasil");
	}	
	
    @DisplayName("Test for Given Car Object when Save then Return Saved Car")
    @Test  
    void testGivenCarObject_whenSave_thenReturnSavedCar() {
                
        // When / Act
        Car savedCar = repository.save(car);
        
        // Then / Assert
        assertNotNull(savedCar);
        assertTrue(savedCar.getId() > 0);
    }
    
    @DisplayName("Test for Given Car List when findAll then Return Car List")
    @Test 
    void testGivenCarList_whenFindAll_thenReturnCarList() {
        
        // Given / Arrange   
    	Car car1 = new Car(2L, "Volkswagen", "Amarok V6 Comfortline", 2024, "Azul Marinho", "CMB7761", 0, "Diesel", "Automática de 8 velocidades", 258, new BigDecimal(3.0), 4,
				"Picape", new BigDecimal(280000.0), LocalDate.of(2024, 9, 24), "Volkswagen do Brasil");
    	
    	repository.save(car);
        repository.save(car1);
        
        // When / Act
        List<Car> CarList = repository.findAll();
        
        // Then / Assert
        assertNotNull(CarList);
        assertEquals(2, CarList.size());
    }
    
    @DisplayName("Test for Given Car Object when findByID then Return Car Object")
    @Test   
    void testGivenCarObject_whenFindByID_thenReturnCarObject() {
        
        // Given / Arrange
    	Car savedCar = repository.save(car);
                
        // When / Act
        Car optionalCar = repository.findById(savedCar.getId()).get();
        
        // Then / Assert
        assertNotNull(optionalCar);
        assertEquals(savedCar.getId(), optionalCar.getId());
    }
       
    @DisplayName("Test for Given Car Object when Update Car then Return Updated Car Object")
    @Test  
    void testGivenCarObject_whenUpdateCar_thenReturnUpdatedCarObject() {
        
        // Given / Arrange
    	Car savedCar = repository.save(car);
        
        // When / Act
        Car foundCar = repository.findById(savedCar.getId()).get();
        foundCar.setColor("Prata");
        foundCar.setFuelType("Elétrico");
        
        Car updatedCar = repository.save(foundCar);
        
        // Then / Assert
        assertNotNull(updatedCar);
        assertEquals("Prata", updatedCar.getColor());
        assertEquals("Elétrico", updatedCar.getFuelType());
    }
    
    @DisplayName("Test for Given Car Object when Delete then Remove Car")
    @Test  
    void testGivenCarObject_whenDelete_thenRemoveCar() {
        
        // Given / Arrange
        Car savedCar = repository.save(car);
        
        // When / Act
        repository.deleteById(savedCar.getId());
        
        Optional<Car> CarOptional = repository.findById(savedCar.getId());
        
        // Then / Assert
        assertTrue(CarOptional.isEmpty());
    }
    
}
