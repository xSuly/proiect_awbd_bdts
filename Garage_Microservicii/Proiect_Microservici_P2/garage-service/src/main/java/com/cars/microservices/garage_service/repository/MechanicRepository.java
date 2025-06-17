package com.cars.microservices.garage_service.repository;

import com.cars.microservices.garage_service.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MechanicRepository extends JpaRepository<Mechanic, Integer> {
    List<Mechanic> findByGarageId(Integer garageId);
}
