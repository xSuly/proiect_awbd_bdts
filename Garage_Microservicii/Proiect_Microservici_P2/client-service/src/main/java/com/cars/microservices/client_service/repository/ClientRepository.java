package com.cars.microservices.client_service.repository;

import com.cars.microservices.client_service.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByVehicleId(Integer vehicleId);

}
