package com.cars.garage.controller;

import com.cars.garage.entity.Vehicle;
import com.cars.garage.service.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    @ResponseBody
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.saveVehicle(vehicle);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Vehicle getVehicleById(@PathVariable Integer id) {
        return vehicleService.getVehicleById(id);
    }

    @GetMapping
    @ResponseBody
    public Page<Vehicle> getAllVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return vehicleService.getAllVehicles(page, size, sortField, sortDir);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Vehicle updateVehicle(@PathVariable Integer id, @RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(id, vehicle);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteVehicleById(@PathVariable Integer id) {
        vehicleService.deleteVehicle(id);
    }

    @GetMapping("/list")
    public String listVehicles(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "id") String sortField,
                               @RequestParam(defaultValue = "asc") String sortDir,
                               HttpServletRequest request) {

        logger.info("Listing vehicles - page: {}, sortField: {}, sortDir: {}", page, sortField, sortDir);

        Page<Vehicle> vehiclePage = vehicleService.getVehiclesPaginated(page, sortField, sortDir);

        model.addAttribute("vehicles", vehiclePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vehiclePage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        if (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
            return "fragments/vehicle_table :: vehicleList";
        }

        return "index";
    }


    /*@PostMapping("/delete")
    public String deleteVehicleFromView(@RequestParam Integer id,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "sortField", defaultValue = "brand") String sortField,
                                        @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                        HttpServletRequest request) {

        logger.info("Deleting vehicle with id {}", id);
        vehicleService.deleteVehicle(id);

        if (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
            return "fragments/vehicle_table :: vehicleList";
        }

        return "redirect:/";
    }*/


}
