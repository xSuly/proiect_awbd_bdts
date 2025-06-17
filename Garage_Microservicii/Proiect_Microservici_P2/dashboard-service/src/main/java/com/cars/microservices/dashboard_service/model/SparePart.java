package com.cars.microservices.dashboard_service.model;

public record SparePart(
        Integer id,
        String name,
        String type,
        Double price
) {
}
