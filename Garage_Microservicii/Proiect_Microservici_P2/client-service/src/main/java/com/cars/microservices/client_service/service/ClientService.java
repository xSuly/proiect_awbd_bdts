package com.cars.microservices.client_service.service;

import com.cars.microservices.client_service.DTO.ClientDTO;
import com.cars.microservices.client_service.DTO.ClientDetailDTO;
import com.cars.microservices.client_service.DTO.VehicleDTO;
import com.cars.microservices.client_service.client.VehicleServiceClient;
import com.cars.microservices.client_service.entity.Client;
import com.cars.microservices.client_service.exception.ResourceNotFoundException;
import com.cars.microservices.client_service.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final VehicleServiceClient vehicleServiceClient;

    public ClientService(ClientRepository clientRepository, VehicleServiceClient vehicleServiceClient) {
        this.clientRepository = clientRepository;
        this.vehicleServiceClient = vehicleServiceClient;
    }

    public Client getClientById(Integer id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client createClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setAge(clientDTO.getAge());
        client.setLocation(clientDTO.getLocation());
        client.setVehicleId(clientDTO.getVehicleId());
        return clientRepository.save(client);
    }

    public ClientDetailDTO getClientWithVehicleDetails(Integer clientId) {
        Client client = getClientById(clientId);

        VehicleDTO vehicle = null;
        if (client.getVehicleId() != null) {
            vehicle = vehicleServiceClient.getVehicleById(client.getVehicleId());
        }

        return new ClientDetailDTO(client, vehicle);
    }

    public Client getClientByVehicleId(Integer vehicleId) {
        return clientRepository.findByVehicleId(vehicleId)
                .orElse(null);
    }
}
