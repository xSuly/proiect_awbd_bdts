package com.cars.microservices.dashboard_service.controller;

import com.cars.microservices.dashboard_service.client.ClientServiceClient;
import com.cars.microservices.dashboard_service.client.GarageServiceClient;
import com.cars.microservices.dashboard_service.client.RepairServiceClient;
import com.cars.microservices.dashboard_service.client.VehicleServiceClient;
import com.cars.microservices.dashboard_service.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;


@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private final VehicleServiceClient vehicleClient;
    private final ClientServiceClient clientClient;
    private final GarageServiceClient garageClient;
    private final RepairServiceClient repairClient;

    public DashboardController(VehicleServiceClient vc, ClientServiceClient cc, GarageServiceClient gc, RepairServiceClient rc) {
        this.vehicleClient = vc;
        this.clientClient = cc;
        this.garageClient = gc;
        this.repairClient = rc;
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @PostMapping("/search")
    public String searchVehicle(@RequestParam("vehicleId") Integer vehicleId) {
        return "redirect:/vehicle/" + vehicleId;
    }

    @GetMapping("/vehicle/{id}")
    public String vehicleDetails(@PathVariable("id") Integer vehicleId, Model model) {
        logger.info("======================================================");
        logger.info("Fetching full details for vehicleId: {}", vehicleId);

        try {
            Vehicle vehicle = vehicleClient.getVehicleById(vehicleId);
            model.addAttribute("vehicle", vehicle);
            logger.info("SUCCESS: Found vehicle -> {}", vehicle);

            try {
                Client client = clientClient.getClientByVehicleId(vehicleId).getBody();
                model.addAttribute("client", client);
                logger.info("SUCCESS: Found client -> {}", client);
            } catch (Exception e) {
                logger.warn("WARN: No client found for vehicleId: {}. Error: {}", vehicleId, e.getMessage());
                model.addAttribute("client", null);
            }

            if (vehicle.garageId() != null) {
                try {
                    Garage garage = garageClient.getGarageById(vehicle.garageId());
                    model.addAttribute("garage", garage);
                    logger.info("SUCCESS: Found garage -> {}", garage);

                    List<Mechanic> mechanics = garageClient.getMechanicsByGarageId(garage.id());
                    model.addAttribute("mechanics", mechanics);
                    logger.info("SUCCESS: Found {} mechanics.", mechanics.size());
                } catch (Exception e) {
                    logger.warn("WARN: Could not fetch garage/mechanics info for garageId: {}. Error: {}", vehicle.garageId(), e.getMessage());
                    model.addAttribute("garage", null);
                    model.addAttribute("mechanics", Collections.emptyList());
                }
            } else {
                model.addAttribute("garage", null);
                model.addAttribute("mechanics", Collections.emptyList());
            }

            try {
                List<RepairRequest> repairs = repairClient.getRepairsByVehicleId(vehicleId);
                model.addAttribute("repairs", repairs);
                logger.info("SUCCESS: Found {} repair requests.", repairs.size());
            } catch (Exception e) {
                logger.warn("WARN: Could not fetch repair history for vehicleId: {}. Error: {}", vehicleId, e.getMessage());
                model.addAttribute("repairs", Collections.emptyList());
            }

            try {
                model.addAttribute("maintenance", vehicleClient.getMaintenanceLogsForVehicle(vehicleId));
                logger.info("SUCCESS: Found maintenance logs.");
            } catch (Exception e) {
                logger.error("ERROR: Failed to fetch maintenance logs for vehicleId: {}. Error: {}", vehicleId, e.getMessage());
                model.addAttribute("maintenance", Collections.emptyList());
            }

            try {
                model.addAttribute("fuel", vehicleClient.getFuelLogsForVehicle(vehicleId));
                logger.info("SUCCESS: Found fuel logs.");
            } catch (Exception e) {
                logger.error("ERROR: Failed to fetch fuel logs for vehicleId: {}. Error: {}", vehicleId, e.getMessage());
                model.addAttribute("fuel", Collections.emptyList());
            }
            logger.info("======================================================");

        } catch (Exception e) {
            logger.error("FATAL: Could not find base vehicle with ID: {}. Aborting.", vehicleId, e);
            model.addAttribute("error", "Nu s-a putut gasi masina cu ID: " + vehicleId);
            return "home";
        }

        return "dashboard";
    }
}
