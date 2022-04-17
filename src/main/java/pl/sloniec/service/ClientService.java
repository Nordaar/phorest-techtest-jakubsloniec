package pl.sloniec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.sloniec.domain.Client;
import pl.sloniec.parser.ClientCSVParser;
import pl.sloniec.repository.ClientRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientCSVParser clientCSVParser;

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

    public void importFromCSV(MultipartFile file) {
        List<Client> clients = clientCSVParser.parse(file);
        clientRepository.saveAll(clients);
    }
}
