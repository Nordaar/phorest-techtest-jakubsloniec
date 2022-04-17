package pl.sloniec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sloniec.domain.Product;
import pl.sloniec.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product getById(UUID id) {
        return productRepository.getById(id);
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public void create(List<Product> products) {
        productRepository.saveAll(products);
    }
}
