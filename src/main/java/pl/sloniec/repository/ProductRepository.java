package pl.sloniec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sloniec.domain.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
