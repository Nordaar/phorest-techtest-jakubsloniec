package pl.sloniec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sloniec.domain.Appointment;
import pl.sloniec.domain.Client;
import pl.sloniec.domain.Product;
import pl.sloniec.repository.ClientRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(UUID id) {
        return clientRepository.findById(id);
    }

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    public void create(List<Client> clients) {
        clientRepository.saveAll(clients);
    }

    public List<Client> getTopClients(Integer limit, LocalDate from) {
        return clientRepository.findAll().stream()
                .filter(not(Client::getBanned))
                .collect(Collectors.groupingBy(
                        client -> countClientLoyaltyPointsFromDate(client, from),
                        () -> new TreeMap<>(Collections.reverseOrder()),
                        Collectors.toList()))
                .values()
                .stream()
                .flatMap(List::stream)
                .limit(limit)
                .collect(Collectors.toList());
    }

    private Integer countClientLoyaltyPointsFromDate(Client client, LocalDate date) {
        return client.getAppointments().stream()
                .filter(appointment -> !appointment.getEndTime().toLocalDate().isBefore(date))
                .map(Appointment::getProducts)
                .flatMap(List::stream)
                .map(Product::getLoyaltyPoints)
                .mapToInt(Integer::intValue).sum();
    }

}
