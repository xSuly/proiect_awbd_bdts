package com.cars.garage.controllerTest;

import com.cars.garage.controller.ClientController;
import com.cars.garage.dto.ClientDTO;
import com.cars.garage.entity.Client;
import com.cars.garage.entity.Vehicle;
import com.cars.garage.exception.ResourceNotFoundException;
import com.cars.garage.service.ClientService;
import com.cars.garage.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClient_withVehicle_shouldReturnCreated() {
        ClientDTO dto = new ClientDTO();
        dto.setName("Client1");
        dto.setVehicleId(1);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);

        Client saved = new Client();
        saved.setId(1);
        saved.setName("Client1");

        when(vehicleService.getVehicleById(1)).thenReturn(vehicle);
        when(clientService.saveClient(any())).thenReturn(saved);

        ResponseEntity<Client> response = clientController.createClient(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void createClient_shouldReturnNotFoundIfVehicleMissing() {
        ClientDTO dto = new ClientDTO();
        dto.setVehicleId(999);

        when(vehicleService.getVehicleById(999)).thenThrow(new ResourceNotFoundException("Vehicle not found"));

        ResponseEntity<Client> response = clientController.createClient(dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getClientById_found_shouldReturnOk() {
        Client client = new Client();
        client.setId(1);

        when(clientService.getClientById(1)).thenReturn(client);

        ResponseEntity<Client> response = clientController.getClientById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void getClientById_notFound_shouldReturn404() {
        when(clientService.getClientById(1)).thenReturn(null);

        ResponseEntity<Client> response = clientController.getClientById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void getAllClients_shouldReturnList() {
        List<Client> clients = List.of(new Client(), new Client());

        when(clientService.getAllClients()).thenReturn(clients);

        List<Client> result = clientController.getAllClients();

        assertEquals(2, result.size());
    }

    @Test
    void updateClient_found_shouldReturnUpdatedClient() {
        ClientDTO dto = new ClientDTO();
        dto.setName("Updated");

        Client existing = new Client();
        existing.setId(1);

        when(clientService.getClientById(1)).thenReturn(existing);
        when(clientService.updateClient(eq(1), any())).thenReturn(existing);

        ResponseEntity<Client> response = clientController.updateClient(1, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateClient_notFound_shouldReturn404() {
        ClientDTO dto = new ClientDTO();
        when(clientService.getClientById(1)).thenThrow(new ResourceNotFoundException("Not found"));

        ResponseEntity<Client> response = clientController.updateClient(1, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteClient_shouldReturnNoContent() {
        ResponseEntity<Void> response = clientController.deleteClient(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clientService).deleteClient(1);
    }

    @Test
    void deleteClient_shouldReturnNotFound() {
        doThrow(new ResourceNotFoundException("Not found")).when(clientService).deleteClient(1);

        ResponseEntity<Void> response = clientController.deleteClient(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
