package com.cars.microservices.garage_service.repository;

import com.cars.microservices.garage_service.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarageRepository extends JpaRepository<Garage, Integer> {
}
