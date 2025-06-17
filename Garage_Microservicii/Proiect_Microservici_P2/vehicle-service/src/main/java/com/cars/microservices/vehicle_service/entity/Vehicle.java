package com.cars.microservices.vehicle_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotNull(message = "Brand cannot be null")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    private String brand;

    @NotNull(message = "Model cannot be null")
    @Size(min = 2, max = 50, message = "Model must be between 2 and 50 characters")
    private String model;

    @NotNull(message = "Year cannot be null")
    @Column(name = "production_year")
    @Min(value = 1900, message = "Year must be greater than or equal to 1900")
    @Max(value = 2100, message = "Year must be less than or equal to 2100")
    private Integer productionYear;

    @NotNull(message = "Type cannot be null")
    @Size(min = 2, max = 50, message = "Type must be between 2 and 50 characters")
    private String type;

    @NotNull(message = "Capacity cannot be null")
    @Positive(message = "Capacity must be a positive number")
    private Integer capacity;

    @NotNull(message = "Status cannot be null")
    @Size(min = 2, max = 20, message = "Status must be between 2 and 20 characters")
    private String status;

    @Column(name = "garage_id")
    private Integer garageId;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<com.cars.microservices.vehicle_service.entity.MaintenanceLog> maintenanceLogs;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<com.cars.microservices.vehicle_service.entity.FuelLog> fuelLogs;

    public Vehicle() {
    }

    public Vehicle(String brand, String model, Integer productionYear, String type,
                   Integer capacity, String status, Integer garageId,
                   List<com.cars.microservices.vehicle_service.entity.MaintenanceLog> maintenanceLogs,
                   List<com.cars.microservices.vehicle_service.entity.FuelLog> fuelLogs) {
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.type = type;
        this.capacity = capacity;
        this.status = status;
        this.garageId = garageId;
        this.maintenanceLogs = maintenanceLogs;
        this.fuelLogs = fuelLogs;
    }

    public Integer getGarageId() {
        return garageId;
    }

    public void setGarageId(Integer garageId) {
        this.garageId = garageId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public List<com.cars.microservices.vehicle_service.entity.MaintenanceLog> getMaintenanceLogs() {
        return maintenanceLogs;
    }

    public void setMaintenanceLogs(List<com.cars.microservices.vehicle_service.entity.MaintenanceLog> maintenanceLogs) {
        this.maintenanceLogs = maintenanceLogs;
    }

    public List<com.cars.microservices.vehicle_service.entity.FuelLog> getFuelLogs() {
        return fuelLogs;
    }

    public void setFuelLogs(List<com.cars.microservices.vehicle_service.entity.FuelLog> fuelLogs) {
        this.fuelLogs = fuelLogs;
    }

}
