package pl.sloniec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.sloniec.domain.Client;
import pl.sloniec.repository.ClientRepository;
import pl.sloniec.service.parser.ClientCSVParser;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientCSVParser clientCSVParser;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    public void create(MultipartFile file) {
        List<Client> clients = clientCSVParser.parse(file);
        clientRepository.saveAll(clients);
    }
}
