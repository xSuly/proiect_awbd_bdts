package com.cars.garage.controllerTest;

import com.cars.garage.controller.VehicleController;
import com.cars.garage.entity.Vehicle;
import com.cars.garage.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleControllerTest {

    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllVehicles() {
        Vehicle vehicle1 = new Vehicle(1, "Toyota", "Corolla", 2020, "Sedan", 5, "Available", null, null, null, null);
        Vehicle vehicle2 = new Vehicle(2, "Ford", "Focus", 2018, "Hatchback", 5, "Available", null, null, null, null);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        Page<Vehicle> vehiclePage = new PageImpl<>(vehicles, PageRequest.of(0, 10), vehicles.size());

        when(vehicleService.getAllVehicles(0, 10, "id", "asc")).thenReturn(vehiclePage);

        Page<Vehicle> result = vehicleController.getAllVehicles(0, 10, "id", "asc");

        assertEquals(2, result.getContent().size());
        assertEquals("Toyota", result.getContent().get(0).getBrand());
        verify(vehicleService, times(1)).getAllVehicles(0, 10, "id", "asc");
    }

    @Test
    void getVehicleById() {
        Vehicle vehicle = new Vehicle(1, "Toyota", "Corolla", 2020, "Sedan", 5, "Available", null, null, null, null);
        when(vehicleService.getVehicleById(1)).thenReturn(vehicle);

        Vehicle result = vehicleController.getVehicleById(1);

        assertEquals("Toyota", result.getBrand());
        verify(vehicleService, times(1)).getVehicleById(1);
    }

    @Test
    void createVehicle() {
        Vehicle vehicle = new Vehicle(null, "Honda", "Civic", 2019, "Sedan", 5, "Available", null, null, null, null);
        when(vehicleService.saveVehicle(vehicle)).thenReturn(vehicle);

        Vehicle result = vehicleController.createVehicle(vehicle);

        assertNotNull(result);
        assertEquals("Honda", result.getBrand());
        verify(vehicleService, times(1)).saveVehicle(vehicle);
    }

    @Test
    void updateVehicle() {
        Vehicle updated = new Vehicle(1, "Toyota", "Corolla", 2021, "Sedan", 5, "Available", null, null, null, null);
        when(vehicleService.updateVehicle(1, updated)).thenReturn(updated);

        Vehicle result = vehicleController.updateVehicle(1, updated);

        assertEquals(2021, result.getProductionYear());
        verify(vehicleService, times(1)).updateVehicle(1, updated);
    }

    @Test
    void deleteVehicle() {
        doNothing().when(vehicleService).deleteVehicle(1);

        vehicleController.deleteVehicleById(1);

        verify(vehicleService, times(1)).deleteVehicle(1);
    }
}
