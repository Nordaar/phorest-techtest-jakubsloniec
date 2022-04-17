package pl.sloniec.delegate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.sloniec.controller.ProductsApiDelegate;
import pl.sloniec.domain.Product;
import pl.sloniec.dto.ProductDTO;
import pl.sloniec.mapper.ProductMapper;
import pl.sloniec.parser.PurchaseCSVParser;
import pl.sloniec.parser.ServiceCSVParser;
import pl.sloniec.service.ProductService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProductsDelegate implements ProductsApiDelegate {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final PurchaseCSVParser purchaseCSVParser;
    private final ServiceCSVParser serviceCSVParser;

    @Override
    public ResponseEntity<List<ProductDTO>> listProducts() {
        return ResponseEntity.ok(productMapper.toDTO(productService.findAll()));
    }

    @Override
    public ResponseEntity<ProductDTO> showProductById(String ProductId) {
        try {
            Product Product = productService.getById(UUID.fromString(ProductId));
            return ResponseEntity.ok(productMapper.toDTO(Product));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ProductDTO> createProduct(ProductDTO ProductDTO) {
        Product product1 = productMapper.fromDTO(ProductDTO);
        Product Product = productService.create(product1);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productMapper.toDTO(Product));
    }

    @Override
    public ResponseEntity<Void> importPurchases(MultipartFile file) {
        List<Product> products = purchaseCSVParser.parse(file);
        productService.create(products);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Void> importServices(MultipartFile file) {
        List<Product> products = serviceCSVParser.parse(file);
        productService.create(products);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
