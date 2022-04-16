package pl.sloniec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.sloniec.domain.Client;
import pl.sloniec.service.ClientService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public List<Client> getAll() {
        return clientService.findAll();
    }

    @PostMapping
    public Client create(@Valid @RequestBody Client client) {
        return clientService.create(client);
    }

    @PostMapping("/import")
    public void importClients(@RequestPart("file") MultipartFile file) {
        clientService.importFromCSV(file);
    }
}
