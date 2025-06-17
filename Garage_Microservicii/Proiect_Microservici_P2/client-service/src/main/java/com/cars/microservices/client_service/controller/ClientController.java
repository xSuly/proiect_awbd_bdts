package com.cars.microservices.client_service.controller;


import com.cars.microservices.client_service.DTO.ClientDTO;
import com.cars.microservices.client_service.DTO.ClientDetailDTO;
import com.cars.microservices.client_service.entity.Client;
import com.cars.microservices.client_service.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody ClientDTO clientDTO) {
        Client savedClient = clientService.createClient(clientDTO);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ClientDetailDTO> getClientWithDetails(@PathVariable Integer id) {
        ClientDetailDTO clientDetails = clientService.getClientWithVehicleDetails(id);
        return ResponseEntity.ok(clientDetails);
    }

    @GetMapping("/by-vehicle/{vehicleId}")
    public ResponseEntity<Client> getClientByVehicleId(@PathVariable Integer vehicleId) {
        Client client = clientService.getClientByVehicleId(vehicleId);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
