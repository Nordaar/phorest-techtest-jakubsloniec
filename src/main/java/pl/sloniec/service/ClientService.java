package pl.sloniec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sloniec.domain.Client;
import pl.sloniec.repository.ClientRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client getById(UUID id) {
        return clientRepository.getById(id);
    }

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    public void create(List<Client> clients) {
        clientRepository.saveAll(clients);
    }
}
