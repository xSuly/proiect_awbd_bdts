package com.cars.garage.serviceTest;

import com.cars.garage.entity.Garage;
import com.cars.garage.entity.Mechanic;
import com.cars.garage.exception.ResourceNotFoundException;
import com.cars.garage.repository.GarageRepository;
import com.cars.garage.repository.MechanicRepository;
import com.cars.garage.service.MechanicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MechanicServiceTest {

    @Mock
    private MechanicRepository mechanicRepository;

    @Mock
    private GarageRepository garageRepository;

    @InjectMocks
    private MechanicService mechanicService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveMechanic_withGarage_shouldSaveMechanic() {
        Garage garage = new Garage();
        garage.setId(1);

        Mechanic mechanic = new Mechanic();
        mechanic.setName("Alex");
        mechanic.setGarage(garage);

        when(garageRepository.findById(1)).thenReturn(Optional.of(garage));
        when(mechanicRepository.save(mechanic)).thenReturn(mechanic);

        Mechanic result = mechanicService.saveMechanic(mechanic);

        assertEquals("Alex", result.getName());
        verify(mechanicRepository, times(1)).save(mechanic);
    }

    @Test
    void getMechanicById_shouldReturnMechanic() {
        Mechanic mechanic = new Mechanic();
        mechanic.setId(1);
        mechanic.setName("Bob");

        when(mechanicRepository.findById(1)).thenReturn(Optional.of(mechanic));

        Mechanic result = mechanicService.getMechanicById(1);

        assertEquals("Bob", result.getName());
    }


    @Test
    void getMechanicById_shouldThrowException() {
        when(mechanicRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mechanicService.getMechanicById(1));
    }

    @Test
    void getAllMechanics_shouldReturnList() {
        List<Mechanic> mechanics = Arrays.asList(new Mechanic(), new Mechanic());

        when(mechanicRepository.findAll()).thenReturn(mechanics);

        List<Mechanic> result = mechanicService.getAllMechanics();

        assertEquals(2, result.size());
    }

    @Test
    void deleteMechanic_shouldDeleteIfExists() {
        Mechanic mechanic = new Mechanic();
        mechanic.setId(1);

        when(mechanicRepository.findById(1)).thenReturn(Optional.of(mechanic));

        mechanicService.deleteMechanic(1);

        verify(mechanicRepository, times(1)).delete(mechanic);
    }

    @Test
    void deleteMechanic_shouldThrowExceptionIfNotFound() {
        when(mechanicRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mechanicService.deleteMechanic(1));
    }

    @Test
    void updateMechanic_shouldUpdateSuccessfully() {
        Garage garage = new Garage();
        garage.setId(1);

        Mechanic existing = new Mechanic();
        existing.setId(1);
        existing.setName("Old");

        Mechanic updated = new Mechanic();
        updated.setName("New");
        updated.setSpecialization("Engine");
        updated.setGarage(garage);

        when(mechanicRepository.findById(1)).thenReturn(Optional.of(existing));
        when(garageRepository.findById(1)).thenReturn(Optional.of(garage));
        when(mechanicRepository.save(any())).thenReturn(existing);

        Mechanic result = mechanicService.updateMechanic(1, updated);

        assertEquals("New", result.getName());
        assertEquals("Engine", result.getSpecialization());
        verify(mechanicRepository, times(1)).save(existing);
    }


    @Test
    void updateMechanic_shouldThrowExceptionIfMechanicNotFound() {
        Mechanic updated = new Mechanic();
        updated.setName("Any");

        when(mechanicRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mechanicService.updateMechanic(1, updated));
    }

    @Test
    void saveMechanic_shouldThrowExceptionIfGarageNotFound() {
        Garage garage = new Garage();
        garage.setId(99);

        Mechanic mechanic = new Mechanic();
        mechanic.setName("Mike");
        mechanic.setGarage(garage);

        when(garageRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mechanicService.saveMechanic(mechanic));
    }
}

