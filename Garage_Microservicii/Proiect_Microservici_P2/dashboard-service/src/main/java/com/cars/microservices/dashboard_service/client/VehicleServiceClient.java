package com.cars.microservices.dashboard_service.client;

import com.cars.microservices.dashboard_service.model.FuelLog;
import com.cars.microservices.dashboard_service.model.MaintenanceLog;
import com.cars.microservices.dashboard_service.model.Vehicle;
import com.cars.microservices.dashboard_service.model.VehiclePageWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "VEHICLE-SERVICE")
public interface VehicleServiceClient {
    @GetMapping("/vehicles/{id}")
    Vehicle getVehicleById(@PathVariable("id") Integer id);

    @GetMapping("/maintenance-logs/by-vehicle/{vehicleId}")
    List<MaintenanceLog> getMaintenanceLogsForVehicle(@PathVariable("vehicleId") Integer vehicleId);

    @GetMapping("/fuel-logs/by-vehicle/{vehicleId}")
    List<FuelLog> getFuelLogsForVehicle(@PathVariable("vehicleId") Integer vehicleId);
}