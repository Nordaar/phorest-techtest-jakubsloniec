package pl.sloniec.delegate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.sloniec.controller.ClientsApiDelegate;
import pl.sloniec.domain.Client;
import pl.sloniec.dto.ClientDTO;
import pl.sloniec.mapper.ClientMapper;
import pl.sloniec.parser.ClientCSVParser;
import pl.sloniec.service.ClientService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ClientsDelegate implements ClientsApiDelegate {

    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final ClientCSVParser clientCSVParser;

    @Override
    public ResponseEntity<List<ClientDTO>> listClients() {
        return ResponseEntity.ok(clientMapper.toDTO(clientService.getAll()));
    }

    @Override
    public ResponseEntity<ClientDTO> showClientById(UUID clientId) {
        return clientService.findById(clientId)
                .map(client -> ResponseEntity.ok(clientMapper.toDTO(client)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ClientDTO> createClients(ClientDTO clientDTO) {
        Client client = clientService.create(clientMapper.fromDTO(clientDTO));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientMapper.toDTO(client));
    }

    @Override
    public ResponseEntity<Void> importClients(MultipartFile file) {
        List<Client> clients = clientCSVParser.parse(file);
        clientService.create(clients);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<ClientDTO>> listTopClients(Integer limit, LocalDate from) {
        return ResponseEntity.ok(clientMapper.toDTO(clientService.getTopClients(limit, from)));
    }
}
