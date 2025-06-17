package com.cars.microservices.garage_service.service;

import com.cars.microservices.garage_service.entity.Garage;
import com.cars.microservices.garage_service.exception.ResourceNotFoundException;
import com.cars.microservices.garage_service.repository.GarageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageService {
    private final GarageRepository garageRepository;

    public GarageService(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    public List<Garage> getAllGarages() {
        return garageRepository.findAll();
    }

    public Garage getGarageById(Integer id) {
        return garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage not found with id: " + id));
    }

    public Garage saveGarage(Garage garage) {
        return garageRepository.save(garage);
    }
}