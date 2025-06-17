package com.cars.microservices.dashboard_service.model;

public record Vehicle(
        Integer id,
        String brand,
        String model,
        Integer productionYear,
        String type,
        Integer capacity,
        String status,
        Integer garageId
) {
}
