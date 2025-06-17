package com.cars.microservices.dashboard_service.model;

import java.time.LocalDate;
import java.util.List;

public record RepairRequest(
        Integer id,
        String issueDescription,
        LocalDate requestDate,
        String status,
        Double totalCost,
        List<SparePart> spareParts
) {
}
