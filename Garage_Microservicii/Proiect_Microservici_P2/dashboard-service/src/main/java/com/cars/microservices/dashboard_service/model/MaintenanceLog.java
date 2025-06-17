package com.cars.microservices.dashboard_service.model;

import java.time.LocalDate;

public record MaintenanceLog(
        Integer id,
        LocalDate date,
        String description,
        Double cost,
        LocalDate nextScheduledDate
) {
}
