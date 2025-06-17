package com.cars.microservices.garage_service.DTO;

public class GarageDTO {

    private String location;
    private Integer capacity;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}