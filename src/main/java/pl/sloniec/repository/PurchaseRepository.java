package pl.sloniec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sloniec.domain.Purchase;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
}
