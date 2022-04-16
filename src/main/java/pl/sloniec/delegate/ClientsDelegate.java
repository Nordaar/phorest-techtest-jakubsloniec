package pl.sloniec.delegate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.sloniec.controller.ClientsApiDelegate;
import pl.sloniec.domain.Client;
import pl.sloniec.dto.ClientDTO;
import pl.sloniec.mapper.ClientMapper;
import pl.sloniec.service.ClientService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ClientsDelegate implements ClientsApiDelegate {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Override
    public ResponseEntity<List<ClientDTO>> listClients() {
        return ResponseEntity.ok(clientMapper.toDTO(clientService.findAll()));
    }

    @Override
    public ResponseEntity<ClientDTO> showClientById(String clientId) {
        try {
            Client client = clientService.getById(UUID.fromString(clientId));
            return ResponseEntity.ok(clientMapper.toDTO(client));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ResponseEntity<ClientDTO> createClients(ClientDTO clientDTO) {
        Client client = clientService.create(clientMapper.fromDTO(clientDTO));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientMapper.toDTO(client));
    }

}
