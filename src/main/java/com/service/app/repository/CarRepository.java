package com.service.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.app.entity.Car;

/**
 * Repository for {@link Car} entities.
 * 
 * Provides CRUD operations and custom queries for {@link Car} entities.
 * 
 * @author Wellington
 * @version 1.0 
 * 
 */
public interface CarRepository extends JpaRepository<Car, Long> {

}
