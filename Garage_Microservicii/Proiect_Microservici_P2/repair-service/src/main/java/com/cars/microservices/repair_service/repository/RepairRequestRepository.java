package com.cars.microservices.repair_service.repository;

import com.cars.microservices.repair_service.entity.RepairRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRequestRepository extends JpaRepository<RepairRequest, Integer> {
    List<RepairRequest> findByVehicleId(Integer vehicleId);

}
