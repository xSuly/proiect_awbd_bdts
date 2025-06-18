package com.cars.garage.service;

import com.cars.garage.entity.SparePart;
import com.cars.garage.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cars.garage.repository.SparePartRepository;

import java.util.List;

@Service
public class SparePartService {

    private final SparePartRepository sparePartRepository;
    private static final Logger logger = LoggerFactory.getLogger(SparePartService.class);

    @Autowired
    public SparePartService(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    public SparePart saveSparePart(SparePart sparePart) {
        logger.info("Saving spare part: {}", sparePart.getName());
        SparePart savedSparePart = sparePartRepository.save(sparePart);
        logger.info("Spare part saved with id: {}", savedSparePart.getId());
        return savedSparePart;
    }

    public SparePart getSparePartById(Integer id) {
        logger.info("Fetching spare part with id: {}", id);
        SparePart sparePart = sparePartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SparePart not found with id: " + id));
        logger.info("Spare part found with id: {}", sparePart.getId());
        return sparePart;
    }

    public List<SparePart> getAllSpareParts() {
        logger.info("Fetching all spare parts");
        List<SparePart> spareParts = sparePartRepository.findAll();
        logger.info("Total spare parts fetched: {}", spareParts.size());
        return spareParts;
    }

    public void deleteSparePart(Integer id) {
        logger.info("Attempting to delete spare part with id: {}", id);
        if (!sparePartRepository.existsById(id)) {
            throw new ResourceNotFoundException("SparePart not found with id: " + id);
        }
        sparePartRepository.deleteById(id);
        logger.info("Spare part with id {} deleted successfully", id);
    }

    public SparePart updateSparePart(Integer id, SparePart sparePart) {
        logger.info("Updating spare part with id: {}", id);

        SparePart existingSparePart = sparePartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SparePart not found with id: " + id));

        existingSparePart.setName(sparePart.getName());
        existingSparePart.setType(sparePart.getType());
        existingSparePart.setPrice(sparePart.getPrice());
        existingSparePart.setStockQuantity(sparePart.getStockQuantity());

        SparePart updatedSparePart = sparePartRepository.save(existingSparePart);
        logger.info("Spare part with id {} updated successfully", updatedSparePart.getId());
        return updatedSparePart;
    }
}
