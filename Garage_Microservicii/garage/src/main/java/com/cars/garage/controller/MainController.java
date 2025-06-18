package com.cars.garage.controller;

import com.cars.garage.dto.RepairRequestDTO;
import com.cars.garage.entity.*;
import com.cars.garage.exception.GarageFullException;
import com.cars.garage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private final GarageService garageService;
    private final VehicleService vehicleService;
    private final MaintenanceLogService maintenanceLogService;
    private final FuelLogService fuelLogService;
    private final SparePartService sparePartService;
    private final RepairRequestService repairRequestService;
    private final MechanicService mechanicService;
    private final ClientService clientService;

    @Autowired
    public MainController(
            GarageService garageService,
            VehicleService vehicleService,
            MaintenanceLogService maintenanceLogService,
            FuelLogService fuelLogService,
            SparePartService sparePartService,
            RepairRequestService repairRequestService,
            MechanicService mechanicService,
            ClientService clientService) {
        this.garageService = garageService;
        this.vehicleService = vehicleService;
        this.maintenanceLogService = maintenanceLogService;
        this.fuelLogService = fuelLogService;
        this.sparePartService = sparePartService;
        this.repairRequestService = repairRequestService;
        this.mechanicService = mechanicService;
        this.clientService = clientService;
    }

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "id") String sortField,
                        @RequestParam(defaultValue = "asc") String sortDirection,
                        Model model) {

        Page<Vehicle> vehiclesPage = vehicleService.getAllVehicles(page, size, sortField, sortDirection);

        model.addAttribute("vehicles", vehiclesPage.getContent());
        model.addAttribute("totalPages", vehiclesPage.getTotalPages());
        model.addAttribute("totalItems", vehiclesPage.getTotalElements());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("garages", garageService.getAllGarages());
        model.addAttribute("maintenanceLogs", maintenanceLogService.getAllMaintenanceLogs());
        model.addAttribute("fuelLogs", fuelLogService.getAllFuelLogs());
        model.addAttribute("spareParts", sparePartService.getAllSpareParts());
        model.addAttribute("repairRequests", repairRequestService.getAllRepairRequests());

        model.addAttribute("size", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("mechanics", mechanicService.getAllMechanics());
        model.addAttribute("clients", clientService.getAllClients());


        return "index";
    }


    @PostMapping("/addGarage")
    public String addGarage(@ModelAttribute Garage garage) {
        garage.setOccupiedSpaces(0);
        garageService.saveGarage(garage);
        return "redirect:/";
    }

    @PostMapping("/addVehicle")
    public String addVehicle(@ModelAttribute Vehicle vehicle,
                             @RequestParam(name = "garageId", required = false) Integer garageId,
                             RedirectAttributes redirectAttributes) {
        try {
            vehicleService.saveVehicleInGarage(vehicle, garageId);
        } catch (GarageFullException e) {
            redirectAttributes.addFlashAttribute("garageError", e.getMessage());
        }

        return "redirect:/";
    }

    @PostMapping("/vehicles/delete")
    public String deleteVehicle(@RequestParam("id") Integer vehicleId) {
        vehicleService.deleteVehicleById(vehicleId);
        return "redirect:/";
    }

    @PostMapping("/addMaintenanceLog")
    public String addMaintenanceLog(@ModelAttribute MaintenanceLog maintenanceLog, @RequestParam("vehicleId") Integer vehicleId) {
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        if (vehicle == null) {
            throw new IllegalArgumentException("Invalid vehicle ID: " + vehicleId);
        }

        maintenanceLog.setVehicle(vehicle);
        maintenanceLogService.saveMaintenanceLog(maintenanceLog);
        return "redirect:/";
    }

    @PostMapping("/addFuelLog")
    public String addFuelLog(@ModelAttribute FuelLog fuelLog, @RequestParam("vehicleId") Integer vehicleId) {
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        if (vehicle == null) {
            throw new IllegalArgumentException("Invalid vehicle ID: " + vehicleId);
        }

        fuelLog.setVehicle(vehicle);
        fuelLogService.saveFuelLog(fuelLog);
        return "redirect:/";
    }

    @PostMapping("/addSparePart")
    public String addSparePart(@ModelAttribute SparePart sparePart) {
        sparePartService.saveSparePart(sparePart);
        return "redirect:/";
    }

    @PostMapping("/addRepairRequest")
    public String addRepairRequest(@ModelAttribute RepairRequestDTO repairRequestDTO) {
        repairRequestService.saveRepairRequest(repairRequestDTO);
        return "redirect:/";
    }

    @PostMapping("/addMechanic")
    public String addMechanic(@ModelAttribute Mechanic mechanic,
                              @RequestParam(name = "garageId", required = false) Integer garageId) {
        if (garageId != null) {
            mechanic.setGarage(garageService.getGarageById(garageId));
        }
        mechanicService.saveMechanic(mechanic);
        return "redirect:/";
    }

    @PostMapping("/updateMechanic")
    public String updateMechanic(@RequestParam Integer id,
                                 @RequestParam String name,
                                 @RequestParam String specialization,
                                 @RequestParam(name = "garageId", required = false) Integer garageId) {

        Mechanic updated = new Mechanic();
        updated.setName(name);
        updated.setSpecialization(specialization);
        if (garageId != null) {
            updated.setGarage(garageService.getGarageById(garageId));
        }
        mechanicService.updateMechanic(id, updated);
        return "redirect:/";
    }

    @PostMapping("/addClient")
    public String addClient(@RequestParam String name,
                            @RequestParam Integer age,
                            @RequestParam String location,
                            @RequestParam(name = "vehicleId", required = false) Integer vehicleId) {

        Client client = new Client();
        client.setName(name);
        client.setAge(age);
        client.setLocation(location);
        if (vehicleId != null) {
            client.setVehicle(vehicleService.getVehicleById(vehicleId));
        }
        clientService.saveClient(client);
        return "redirect:/";
    }
}
