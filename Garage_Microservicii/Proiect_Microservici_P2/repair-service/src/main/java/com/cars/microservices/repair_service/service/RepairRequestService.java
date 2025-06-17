package com.cars.microservices.repair_service.service;

import com.cars.microservices.repair_service.client.VehicleServiceClient;
import com.cars.microservices.repair_service.DTO.RepairRequestDTO;
import com.cars.microservices.repair_service.entity.RepairRequest;
import com.cars.microservices.repair_service.entity.SparePart;
import com.cars.microservices.repair_service.exception.ResourceNotFoundException;
import com.cars.microservices.repair_service.repository.RepairRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RepairRequestService {
    private final RepairRequestRepository repairRequestRepository;
    private final SparePartService sparePartService;
    private final VehicleServiceClient vehicleServiceClient;

    public RepairRequestService(RepairRequestRepository r, SparePartService s, VehicleServiceClient v) {
        this.repairRequestRepository = r;
        this.sparePartService = s;
        this.vehicleServiceClient = v;
    }

    public RepairRequest createRepairRequest(RepairRequestDTO dto) {
        try {
            vehicleServiceClient.getVehicleById(dto.getVehicleId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Vehicle not found with id: " + dto.getVehicleId());
        }

        List<SparePart> parts = sparePartService.getPartsByIds(dto.getSparePartIds());

        RepairRequest request = new RepairRequest();
        request.setVehicleId(dto.getVehicleId());
        request.setIssueDescription(dto.getIssueDescription());
        request.setRequestDate(LocalDate.now());
        request.setStatus("Pending");
        request.setTotalCost(dto.getTotalCost());
        request.setSpareParts(parts);

        return repairRequestRepository.save(request);
    }

    public List<RepairRequest> getAllRepairRequests() {
        return repairRequestRepository.findAll();
    }

    public List<RepairRequest> getRepairsByVehicleId(Integer vehicleId) {
        return repairRequestRepository.findByVehicleId(vehicleId);
    }

    public List<SparePart> getSparePartsForRequest(Integer requestId) {
        RepairRequest request = repairRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("RepairRequest not found with id: " + requestId));
        return request.getSpareParts();
    }
}
