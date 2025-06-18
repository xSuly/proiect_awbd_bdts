package com.cars.garage.service;

import com.cars.garage.entity.Client;
import com.cars.garage.entity.Vehicle;
import com.cars.garage.exception.ResourceNotFoundException;
import com.cars.garage.repository.ClientRepository;
import com.cars.garage.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    public ClientService(ClientRepository clientRepository, VehicleRepository vehicleRepository) {
        this.clientRepository = clientRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Client saveClient(Client client) {
        logger.info("Saving client with name: {}", client.getName());

        if (client.getVehicle() != null) {
            Integer vehicleId = client.getVehicle().getId();
            Vehicle vehicle = vehicleRepository.findById(vehicleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + vehicleId));
            client.setVehicle(vehicle);
            logger.info("Assigned vehicle with id: {} to client with name: {}", vehicleId, client.getName());
        }

        Client savedClient = clientRepository.save(client);
        logger.info("Client saved with id: {}", savedClient.getId());
        return savedClient;
    }

    public Client getClientById(Integer id) {
        logger.info("Fetching client with id: {}", id);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        logger.info("Client fetched: {}", client.getName());
        return client;
    }

    public List<Client> getAllClients() {
        logger.info("Fetching all clients");

        List<Client> clients = clientRepository.findAll();
        logger.info("Total clients fetched: {}", clients.size());
        return clients;
    }

    public void deleteClient(Integer id) {
        logger.info("Attempting to delete client with id: {}", id);

        if (!clientRepository.existsById(id)) {
            logger.error("Client with id {} not found for deletion", id);
            throw new ResourceNotFoundException("Client not found with id: " + id);
        }

        clientRepository.deleteById(id);
        logger.info("Client with id {} deleted successfully", id);
    }

    public Client updateClient(Integer id, Client updatedClient) {
        logger.info("Updating client with id: {}", id);

        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        existingClient.setName(updatedClient.getName());
        existingClient.setLocation(updatedClient.getLocation());
        existingClient.setAge(updatedClient.getAge());
        logger.info("Client details updated for id: {}", id);

        if (updatedClient.getVehicle() != null) {
            Integer vehicleId = updatedClient.getVehicle().getId();
            Vehicle vehicle = vehicleRepository.findById(vehicleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + vehicleId));
            existingClient.setVehicle(vehicle);
            logger.info("Assigned vehicle with id: {} to client with id: {}", vehicleId, id);
        } else {
            existingClient.setVehicle(null);
            logger.info("No vehicle assigned to client with id: {}", id);
        }

        Client savedClient = clientRepository.save(existingClient);
        logger.info("Client updated with id: {}", savedClient.getId());
        return savedClient;
    }
}
