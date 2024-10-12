package com.service.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a car entity in the application.
 * 
 * <p>This class is annotated with {@link Entity} and {@link Table} to specify that it
 * represents a database entity mapped to the "cars" table. It implements {@link Serializable}
 * for object serialization.</p>
 *  
 * <p>This class provides necessary details about a car and can be extended to include
 * additional attributes as required by the application.</p>
 * 
 * @author Wellington
 * @version 1.0
 * 
 */
@Entity
@Table(name = "cars")
public class Car implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(name = "model_year", nullable = false, length = 4)
    private int modelYear;

    @Column(length = 50) 
    private String color;

    @Column(name = "license_plate", unique = true, length = 15)
    private String licensePlate;

    @Column 
    private int odometer;

    @Column(name = "fuel_type", length = 50)
    private String fuelType;

    @Column(name = "transmission", length = 50)
    private String transmission;

    @Column (name = "horse_power")
    private int horsePower;
    
    @Column(name = "engine_capacity", precision = 2, scale = 1)
    private BigDecimal engineCapacity;

    @Column(name = "number_of_doors")
    private int numberOfDoors;

    @Column(name = "body_type", length = 50)
    private String bodyType;

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "manufacture_date") 
    private LocalDate manufactureDate;

    @Column(length = 100)
    private String owner;

	public Car(Long id, String brand, String model, int modelYear, String color, String licensePlate, int odometer,
			String fuelType, String transmission, int horsePower, BigDecimal engineCapacity, int numberOfDoors,
			String bodyType, BigDecimal price, LocalDate manufactureDate, String owner) {
		
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.modelYear = modelYear;
		this.color = color;
		this.licensePlate = licensePlate;
		this.odometer = odometer;
		this.fuelType = fuelType;
		this.transmission = transmission;
		this.horsePower = horsePower;
		this.engineCapacity = engineCapacity;
		this.numberOfDoors = numberOfDoors;
		this.bodyType = bodyType;
		this.price = price;
		this.manufactureDate = manufactureDate;
		this.owner = owner;
	}
	
	public Car() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getModelYear() {
		return modelYear;
	}

	public void setModelYear(int modelYear) {
		this.modelYear = modelYear;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public int getOdometer() {
		return odometer;
	}

	public void setOdometer(int odometer) {
		this.odometer = odometer;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public int getHorsePower() {
		return horsePower;
	}

	public void setHorsePower(int horsePower) {
		this.horsePower = horsePower;
	}

	public BigDecimal getEngineCapacity() {
		return engineCapacity;
	}

	public void setEngineCapacity(BigDecimal engineCapacity) {
		this.engineCapacity = engineCapacity;
	}

	public int getNumberOfDoors() {
		return numberOfDoors;
	}

	public void setNumberOfDoors(int numberOfDoors) {
		this.numberOfDoors = numberOfDoors;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public LocalDate getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(LocalDate manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bodyType, brand, color, engineCapacity, fuelType, horsePower, id, licensePlate,
				manufactureDate, model, modelYear, numberOfDoors, odometer, owner, price, transmission);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		return Objects.equals(bodyType, other.bodyType) && Objects.equals(brand, other.brand)
				&& Objects.equals(color, other.color) && Objects.equals(engineCapacity, other.engineCapacity)
				&& Objects.equals(fuelType, other.fuelType) && horsePower == other.horsePower
				&& Objects.equals(id, other.id) && Objects.equals(licensePlate, other.licensePlate)
				&& Objects.equals(manufactureDate, other.manufactureDate) && Objects.equals(model, other.model)
				&& modelYear == other.modelYear && numberOfDoors == other.numberOfDoors && odometer == other.odometer
				&& Objects.equals(owner, other.owner) && Objects.equals(price, other.price)
				&& Objects.equals(transmission, other.transmission);
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", brand=" + brand + ", model=" + model + ", modelYear=" + modelYear + ", color="
				+ color + ", licensePlate=" + licensePlate + ", odometer=" + odometer + ", fuelType=" + fuelType
				+ ", transmission=" + transmission + ", horsePower=" + horsePower + ", engineCapacity=" + engineCapacity
				+ ", numberOfDoors=" + numberOfDoors + ", bodyType=" + bodyType + ", price=" + price
				+ ", manufactureDate=" + manufactureDate + ", owner=" + owner + "]";
	}
	
}
