package com.cars.microservices.dashboard_service.model;

import java.time.LocalDate;

public record FuelLog(
        Integer id,
        LocalDate date,
        Double fuelAddedLiters,
        Double costPerLiter,
        Double totalCost
) {
}
