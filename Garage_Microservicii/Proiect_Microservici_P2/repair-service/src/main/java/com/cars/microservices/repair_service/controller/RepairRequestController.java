package com.cars.microservices.repair_service.controller;

import com.cars.microservices.repair_service.DTO.RepairRequestDTO;
import com.cars.microservices.repair_service.entity.RepairRequest;
import com.cars.microservices.repair_service.entity.SparePart;
import com.cars.microservices.repair_service.service.RepairRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repair-requests")
public class RepairRequestController {

    private final RepairRequestService repairRequestService;

    public RepairRequestController(RepairRequestService repairRequestService) {
        this.repairRequestService = repairRequestService;
    }

    @GetMapping
    public List<RepairRequest> getAllRepairRequests() {
        return repairRequestService.getAllRepairRequests();
    }

    @PostMapping
    public ResponseEntity<RepairRequest> createRepairRequest(@RequestBody RepairRequestDTO repairRequestDTO) {
        RepairRequest savedRequest = repairRequestService.createRepairRequest(repairRequestDTO);
        return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
    }

    @GetMapping("/by-vehicle/{vehicleId}")
    public List<RepairRequest> getRepairsByVehicleId(@PathVariable Integer vehicleId) {
        return repairRequestService.getRepairsByVehicleId(vehicleId);
    }

    @GetMapping("/{requestId}/spare-parts")
    public List<SparePart> getSparePartsForRequest(@PathVariable Integer requestId) {
        return repairRequestService.getSparePartsForRequest(requestId);
    }
}