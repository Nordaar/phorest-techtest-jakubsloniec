package pl.sloniec.mapper;

import org.mapstruct.Mapper;
import pl.sloniec.domain.Client;
import pl.sloniec.dto.ClientDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client fromDTO(ClientDTO clientDto);

    List<Client> fromDTO(List<ClientDTO> clientDtos);

    ClientDTO toDTO(Client client);

    List<ClientDTO> toDTO(List<Client> clients);

}
