package com.service.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.app.entity.Car;
import com.service.app.exceptions.ResourceNotFoundException;
import com.service.app.service.CarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Car Service endpoint")
@RestController
@RequestMapping("/car-service")
public class CarController {

	@Autowired
    private CarService service;	
	
	@Operation(summary="Find all cars")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Car> findAll() {
		return service.findAllCars();
	}
	
	@Operation(summary="Find specific car by your ID")
	@GetMapping(value = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> findById(@PathVariable(value = "id") Long id) {	
		 try {			 
			 	Car foundCar =service.getCarById(id); 		  
		  		return ResponseEntity.ok(foundCar);
		 } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
	}
	
	@Operation(summary="Create a new car")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Car create(@RequestBody Car car) {				
		return service.createCar(car);
	}
	
	@Operation(summary="Update a car")
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> update(@RequestBody Car car) {        
	    try {
            Car updatedCar = service.updateCar(car);
            return ResponseEntity.ok(updatedCar);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
	
	@Operation(summary="Delete a car by your ID")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.deleteCarById(id);
		return ResponseEntity.noContent().build();
	}	
}
