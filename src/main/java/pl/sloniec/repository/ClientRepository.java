package pl.sloniec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sloniec.domain.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
