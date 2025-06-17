package com.cars.microservices.client_service.DTO;

import com.cars.microservices.client_service.entity.Client;

public record ClientDetailDTO(Client client, VehicleDTO vehicle) {
}
