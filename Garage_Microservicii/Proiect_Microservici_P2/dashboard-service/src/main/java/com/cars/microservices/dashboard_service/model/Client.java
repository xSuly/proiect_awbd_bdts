package com.cars.microservices.dashboard_service.model;

public record Client(
        Integer id,
        String name,
        Integer age,
        String location,
        Integer vehicleId
) {
}
