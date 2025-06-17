package com.cars.microservices.repair_service.repository;

import com.cars.microservices.repair_service.entity.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Integer> {

}
