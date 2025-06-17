package com.cars.microservices.vehicle_service.DTO;

import com.cars.microservices.vehicle_service.entity.Vehicle;

public record VehicleDetailDTO(Vehicle vehicle, GarageDTO garage) {
}
