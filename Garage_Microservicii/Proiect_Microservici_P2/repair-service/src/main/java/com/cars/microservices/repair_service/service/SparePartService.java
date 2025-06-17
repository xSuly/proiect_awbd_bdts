package com.cars.microservices.repair_service.service;

import com.cars.microservices.repair_service.DTO.SparePartDTO;
import com.cars.microservices.repair_service.entity.SparePart;
import com.cars.microservices.repair_service.exception.ResourceNotFoundException;
import com.cars.microservices.repair_service.repository.SparePartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SparePartService {

    private final SparePartRepository sparePartRepository;

    public SparePartService(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    public List<SparePart> getAllSpareParts() {
        return sparePartRepository.findAll();
    }

    public SparePart getSparePartById(Integer id) {
        return sparePartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SparePart not found with id: " + id));
    }

    public SparePart createSparePart(SparePartDTO sparePartDTO) {
        SparePart newPart = new SparePart();
        newPart.setName(sparePartDTO.getName());
        newPart.setType(sparePartDTO.getType());
        newPart.setPrice(sparePartDTO.getPrice());
        newPart.setStockQuantity(sparePartDTO.getStockQuantity());

        SparePart savedPart = sparePartRepository.save(newPart);
        return savedPart;
    }

    public SparePart updateSparePart(Integer id, SparePartDTO sparePartDTO) {
        SparePart existingPart = getSparePartById(id);

        existingPart.setName(sparePartDTO.getName());
        existingPart.setType(sparePartDTO.getType());
        existingPart.setPrice(sparePartDTO.getPrice());
        existingPart.setStockQuantity(sparePartDTO.getStockQuantity());

        SparePart updatedPart = sparePartRepository.save(existingPart);
        return updatedPart;
    }

    public void deleteSparePart(Integer id) {
        if (!sparePartRepository.existsById(id)) {
            throw new ResourceNotFoundException("SparePart not found with id: " + id);
        }
        sparePartRepository.deleteById(id);
    }

    public List<SparePart> getPartsByIds(List<Integer> ids) {
        return ids.stream()
                .map(this::getSparePartById)
                .collect(Collectors.toList());
    }
}
