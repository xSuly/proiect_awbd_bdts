package com.cars.microservices.dashboard_service.client;

import com.cars.microservices.dashboard_service.model.RepairRequest;
import com.cars.microservices.dashboard_service.model.SparePart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "REPAIR-SERVICE")
public interface RepairServiceClient {
    @GetMapping("/repair-requests/by-vehicle/{vehicleId}")
    List<RepairRequest> getRepairsByVehicleId(@PathVariable("vehicleId") Integer vehicleId);

    @GetMapping("/repair-requests/{requestId}/spare-parts")
    List<SparePart> getSparePartsForRequest(@PathVariable("requestId") Integer requestId);
}
