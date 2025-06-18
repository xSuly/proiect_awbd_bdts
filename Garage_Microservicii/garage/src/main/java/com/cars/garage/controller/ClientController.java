package com.cars.garage.controller;

import com.cars.garage.dto.ClientDTO;
import com.cars.garage.entity.Client;
import com.cars.garage.entity.Vehicle;
import com.cars.garage.exception.ResourceNotFoundException;
import com.cars.garage.service.ClientService;
import com.cars.garage.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final VehicleService vehicleService;

    @Autowired
    public ClientController(ClientService clientService, VehicleService vehicleService) {
        this.clientService = clientService;
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody ClientDTO clientDTO) {
        try {
            Client client = new Client();
            client.setName(clientDTO.getName());
            client.setAge(clientDTO.getAge());
            client.setLocation(clientDTO.getLocation());

            if (clientDTO.getVehicleId() != null) {
                Vehicle vehicle = vehicleService.getVehicleById(clientDTO.getVehicleId());
                client.setVehicle(vehicle);
            }

            Client savedClient = clientService.saveClient(client);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id) {
        Optional<Client> client = Optional.ofNullable(clientService.getClientById(id));
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer id, @RequestBody ClientDTO clientDTO) {
        try {
            Client existingClient = clientService.getClientById(id);
            existingClient.setName(clientDTO.getName());
            existingClient.setAge(clientDTO.getAge());
            existingClient.setLocation(clientDTO.getLocation());

            if (clientDTO.getVehicleId() != null) {
                Vehicle vehicle = vehicleService.getVehicleById(clientDTO.getVehicleId());
                existingClient.setVehicle(vehicle);
            } else {
                existingClient.setVehicle(null);
            }

            Client updatedClient = clientService.updateClient(id, existingClient);
            return ResponseEntity.ok(updatedClient);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
