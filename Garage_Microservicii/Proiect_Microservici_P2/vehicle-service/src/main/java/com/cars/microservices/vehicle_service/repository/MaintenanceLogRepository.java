package com.cars.microservices.vehicle_service.repository;

import com.cars.microservices.vehicle_service.entity.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Integer> {
    List<MaintenanceLog> findByVehicleId(Integer vehicleId);
}

