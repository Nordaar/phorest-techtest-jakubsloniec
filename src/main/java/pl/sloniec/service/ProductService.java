package pl.sloniec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sloniec.domain.Product;
import pl.sloniec.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public void create(List<Product> products) {
        productRepository.saveAll(products);
    }

    public Product upsert(UUID id, Product newProduct) {
        return productRepository.findById(id).map(product -> {
            product.setAppointmentId(newProduct.getAppointmentId());
            product.setName(newProduct.getName());
            product.setPrice(newProduct.getPrice());
            product.setLoyaltyPoints(newProduct.getLoyaltyPoints());
            product.setProductType(newProduct.getProductType());

            return productRepository.save(product);
        }).orElseGet(() -> {
            newProduct.setId(id);
            return productRepository.save(newProduct);
        });
    }

    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }
}
