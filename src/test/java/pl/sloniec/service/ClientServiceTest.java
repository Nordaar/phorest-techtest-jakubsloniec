package pl.sloniec.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sloniec.domain.Appointment;
import pl.sloniec.domain.Client;
import pl.sloniec.domain.Product;
import pl.sloniec.repository.ClientRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void shouldReturnAllClients() {
        // expected
        var expected = List.of(Client.builder().build());
        given(clientRepository.findAll()).willReturn(expected);

        // when
        List<Client> actual = clientService.getAll();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnTopClients() {
        // given
        var client1 = buildClient(2019, 50);
        var client2 = buildClient(2020, 10);
        var client3 = buildClient(2021, 30);
        given(clientRepository.findAll()).willReturn(List.of(client1, client2, client3));

        var expected = List.of(client3, client2);

        // when
        List<Client> actual = clientService.getTopClients(2, LocalDate.of(2020, 1, 1));

        // then
        assertEquals(expected, actual);
    }

    private Client buildClient(int year, int loyaltyPoints) {
        return Client.builder()
                .banned(false)
                .appointments(List.of(
                        Appointment.builder()
                                .endTime(OffsetDateTime.of(
                                        LocalDate.of(year, 2, 2),
                                        LocalTime.of(1, 1, 1),
                                        ZoneOffset.UTC))
                                .products(List.of(
                                        Product.builder()
                                                .loyaltyPoints(loyaltyPoints)
                                                .build()))
                                .build())
                ).build();
    }

    // TODO other unit tests

}