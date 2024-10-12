package com.service.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.app.entity.Car;
import com.service.app.exceptions.ResourceNotFoundException;
import com.service.app.repository.CarRepository;

/**
 * Service for managing {@link Car} entities.
 * 
 * @author Wellington
 * @version 1.0
 */
@Service
public class CarService {
	
	@Autowired
	private CarRepository repository;	
	
	/**
     * Retrieves a {@link Car} entity by its ID.
     * 
     * @param id the ID of the {@link Car} entity
     * @return an {@link Optional} containing the {@link Car} entity if found, otherwise an empty {@link Optional}
     */
    public Car getCarById(Long id) {
        return repository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    /**
     * Creates a {@link Car} entity.
     * 
     * @param car the {@link Car} entity to create
     * @return the saved {@link Car} entity
     */
    public Car createCar(Car car) {
    	    	
    	Optional<Car> savedCar = repository.findById(car.getId());
		
    	if(savedCar.isPresent()) {
		    throw new ResourceNotFoundException(
	            "Car already exist with given ID: " + car.getId());
		}
    	
        return repository.save(car);
    }
    
    /**
     * Updates a {@link Car} entity.
     * 
     * @param car the {@link Car} entity to update
     * @return the updated {@link Car} entity
     */
    public Car updateCar(Car car) {
    	
    	repository.findById(car.getId())
    			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    	
        return repository.save(car);
    }

    /**
     * Deletes a {@link Car} entity by its ID.
     * 
     * @param id the ID of the {@link Car} entity to delete
     */
    public void deleteCarById(Long id) {
    	var entity = repository.findById(id)
    			     .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    			     repository.delete(entity);
    }

    /**
     * Retrieves all visible {@link Car} entities.
     * 
     * @return a list of all {@link Car} entities
     */
    public List<Car> findAllCars() {
        return repository.findAll();
    }

}
