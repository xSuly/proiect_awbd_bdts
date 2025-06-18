package com.cars.garage.serviceTest;

import com.cars.garage.entity.Client;
import com.cars.garage.entity.Vehicle;
import com.cars.garage.exception.ResourceNotFoundException;
import com.cars.garage.repository.ClientRepository;
import com.cars.garage.repository.VehicleRepository;
import com.cars.garage.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveClient_withVehicle_shouldSaveClient() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);

        Client client = new Client();
        client.setName("John");
        client.setVehicle(vehicle);

        when(vehicleRepository.findById(1)).thenReturn(Optional.of(vehicle));
        when(clientRepository.save(client)).thenReturn(client);

        Client result = clientService.saveClient(client);

        assertEquals("John", result.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void getClientById_shouldReturnClient() {
        Client client = new Client();
        client.setId(1);
        client.setName("Alice");

        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Client result = clientService.getClientById(1);

        assertEquals("Alice", result.getName());
    }

    @Test
    void getClientById_shouldThrowException() {
        when(clientRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.getClientById(1));
    }

    @Test
    void getAllClients_shouldReturnList() {
        List<Client> clients = Arrays.asList(new Client(), new Client());

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        assertEquals(2, result.size());
    }

    @Test
    void deleteClient_shouldDeleteIfExists() {
        when(clientRepository.existsById(1)).thenReturn(true);

        clientService.deleteClient(1);

        verify(clientRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteClient_shouldThrowExceptionIfNotFound() {
        when(clientRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> clientService.deleteClient(1));
    }

    @Test
    void updateClient_shouldUpdateSuccessfully() {
        Client existing = new Client();
        existing.setId(1);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);

        Client updated = new Client();
        updated.setName("New");
        updated.setLocation("Loc");
        updated.setAge(30);
        updated.setVehicle(vehicle);

        when(clientRepository.findById(1)).thenReturn(Optional.of(existing));
        when(vehicleRepository.findById(1)).thenReturn(Optional.of(vehicle));
        when(clientRepository.save(any())).thenReturn(existing);

        Client result = clientService.updateClient(1, updated);

        assertEquals("New", result.getName());
        verify(clientRepository, times(1)).save(existing);
    }
}
