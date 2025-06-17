package com.cars.microservices.vehicle_service.repository;

import com.cars.microservices.vehicle_service.entity.FuelLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelLogRepository extends JpaRepository<FuelLog, Integer> {
    List<FuelLog> findByVehicleId(Integer vehicleId);
}

