package com.cars.garage.service;

import com.cars.garage.dto.RepairRequestDTO;
import com.cars.garage.entity.RepairRequest;
import com.cars.garage.entity.SparePart;
import com.cars.garage.entity.Vehicle;
import com.cars.garage.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cars.garage.repository.RepairRequestRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairRequestService {

    private final RepairRequestRepository repairRequestRepository;
    private final VehicleService vehicleService;
    private final SparePartService sparePartService;
    private static final Logger logger = LoggerFactory.getLogger(RepairRequestService.class);

    @Autowired
    public RepairRequestService(RepairRequestRepository repairRequestRepository, VehicleService vehicleService, SparePartService sparePartService) {
        this.repairRequestRepository = repairRequestRepository;
        this.vehicleService = vehicleService;
        this.sparePartService = sparePartService;
    }

    public RepairRequest saveRepairRequest(RepairRequestDTO repairRequestDTO) {
        logger.info("Saving repair request for vehicle with id: {}", repairRequestDTO.getVehicleId());

        Vehicle vehicle = vehicleService.getVehicleById(repairRequestDTO.getVehicleId());
        List<SparePart> spareParts;
        if (repairRequestDTO.getSparePartIds() != null) {
            spareParts = repairRequestDTO.getSparePartIds().stream()
                    .map(sparePartService::getSparePartById)
                    .collect(Collectors.toList());
        } else {
            spareParts = Collections.emptyList();
        }

        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setVehicle(vehicle);
        repairRequest.setIssueDescription(repairRequestDTO.getIssueDescription());
        repairRequest.setRequestDate(LocalDate.now());
        repairRequest.setStatus(repairRequestDTO.getStatus());
        repairRequest.setTotalCost(repairRequestDTO.getTotalCost());
        repairRequest.setSpareParts(spareParts);

        RepairRequest savedRepairRequest = repairRequestRepository.save(repairRequest);
        logger.info("Repair request saved with id: {}", savedRepairRequest.getId());
        return savedRepairRequest;
    }

    public RepairRequest getRepairRequestById(Integer id) {
        logger.info("Fetching repair request with id: {}", id);
        RepairRequest repairRequest = repairRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RepairRequest not found with id: " + id));
        logger.info("Repair request found with id: {}", repairRequest.getId());
        return repairRequest;
    }

    public List<RepairRequest> getAllRepairRequests() {
        logger.info("Fetching all repair requests");
        List<RepairRequest> repairRequests = repairRequestRepository.findAll();
        logger.info("Total repair requests fetched: {}", repairRequests.size());
        return repairRequests;
    }

    public void deleteRepairRequest(Integer id) {
        logger.info("Attempting to delete repair request with id: {}", id);
        if (!repairRequestRepository.existsById(id)) {
            throw new ResourceNotFoundException("RepairRequest not found with id: " + id);
        }
        repairRequestRepository.deleteById(id);
        logger.info("Repair request with id {} deleted successfully", id);
    }

    public RepairRequest updateRepairRequest(Integer id, RepairRequestDTO repairRequestDTO) {
        logger.info("Updating repair request with id: {}", id);

        RepairRequest existingRepairRequest = repairRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RepairRequest not found with id: " + id));

        Vehicle vehicle = vehicleService.getVehicleById(repairRequestDTO.getVehicleId());
        List<SparePart> spareParts = repairRequestDTO.getSparePartIds().stream()
                .map(sparePartService::getSparePartById)
                .toList();

        existingRepairRequest.setVehicle(vehicle);
        existingRepairRequest.setIssueDescription(repairRequestDTO.getIssueDescription());
        existingRepairRequest.setRequestDate(repairRequestDTO.getRequestDate());
        existingRepairRequest.setStatus(repairRequestDTO.getStatus());
        existingRepairRequest.setTotalCost(repairRequestDTO.getTotalCost());

        existingRepairRequest.getSpareParts().clear();
        existingRepairRequest.getSpareParts().addAll(spareParts);

        RepairRequest updatedRepairRequest = repairRequestRepository.save(existingRepairRequest);
        logger.info("Repair request with id {} updated successfully", updatedRepairRequest.getId());
        return updatedRepairRequest;
    }
}
