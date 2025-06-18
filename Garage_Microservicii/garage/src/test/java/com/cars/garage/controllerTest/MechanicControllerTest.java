package com.cars.garage.controllerTest;

import com.cars.garage.controller.MechanicController;
import com.cars.garage.dto.MechanicDTO;
import com.cars.garage.entity.Garage;
import com.cars.garage.entity.Mechanic;
import com.cars.garage.exception.ResourceNotFoundException;
import com.cars.garage.service.GarageService;
import com.cars.garage.service.MechanicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MechanicControllerTest {

    @Mock
    private MechanicService mechanicService;

    @Mock
    private GarageService garageService;

    @InjectMocks
    private MechanicController mechanicController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMechanic_withGarage_shouldReturnCreated() {
        MechanicDTO dto = new MechanicDTO();
        dto.setName("John");
        dto.setSpecialization("Engine");
        dto.setGarageId(1);

        Garage garage = new Garage();
        garage.setId(1);

        Mechanic saved = new Mechanic();
        saved.setId(1);
        saved.setName("John");
        saved.setSpecialization("Engine");

        when(garageService.getGarageById(1)).thenReturn(garage);
        when(mechanicService.saveMechanic(any())).thenReturn(saved);

        ResponseEntity<Mechanic> response = mechanicController.createMechanic(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John", response.getBody().getName());
    }

    @Test
    void createMechanic_missingGarage_shouldReturnNotFound() {
        MechanicDTO dto = new MechanicDTO();
        dto.setGarageId(999);

        when(garageService.getGarageById(999)).thenThrow(new ResourceNotFoundException("Garage not found"));

        ResponseEntity<Mechanic> response = mechanicController.createMechanic(dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getMechanicById_found_shouldReturnOk() {
        Mechanic mechanic = new Mechanic();
        mechanic.setId(1);

        when(mechanicService.getMechanicById(1)).thenReturn(mechanic);

        ResponseEntity<Mechanic> response = mechanicController.getMechanicById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void getMechanicById_notFound_shouldReturn404() {
        when(mechanicService.getMechanicById(1)).thenThrow(new ResourceNotFoundException("Not found"));

        ResponseEntity<Mechanic> response = mechanicController.getMechanicById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllMechanics_shouldReturnList() {
        List<Mechanic> mechanics = List.of(new Mechanic(), new Mechanic());

        when(mechanicService.getAllMechanics()).thenReturn(mechanics);

        ResponseEntity<List<Mechanic>> response = mechanicController.getAllMechanics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void updateMechanic_found_shouldReturnUpdatedMechanic() {
        MechanicDTO dto = new MechanicDTO();
        dto.setName("Updated");
        dto.setSpecialization("Brakes");

        Mechanic updated = new Mechanic();
        updated.setId(1);
        updated.setName("Updated");

        when(mechanicService.updateMechanic(eq(1), any())).thenReturn(updated);

        ResponseEntity<Mechanic> response = mechanicController.updateMechanic(1, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated", response.getBody().getName());
    }

    @Test
    void updateMechanic_notFound_shouldReturn404() {
        MechanicDTO dto = new MechanicDTO();
        dto.setName("Updated");

        when(mechanicService.updateMechanic(eq(1), any())).thenThrow(new ResourceNotFoundException("Not found"));

        ResponseEntity<Mechanic> response = mechanicController.updateMechanic(1, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteMechanic_shouldReturnNoContent() {
        ResponseEntity<Void> response = mechanicController.deleteMechanic(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(mechanicService).deleteMechanic(1);
    }

    @Test
    void deleteMechanic_shouldReturnNotFound() {
        doThrow(new ResourceNotFoundException("Not found")).when(mechanicService).deleteMechanic(1);

        ResponseEntity<Void> response = mechanicController.deleteMechanic(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

