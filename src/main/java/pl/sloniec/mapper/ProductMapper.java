package pl.sloniec.mapper;

import org.mapstruct.Mapper;
import pl.sloniec.domain.Product;
import pl.sloniec.dto.ProductDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product fromDTO(ProductDTO productDTO);

    List<Product> fromDTO(List<ProductDTO> productDTOs);

    ProductDTO toDTO(Product Product);

    List<ProductDTO> toDTO(List<Product> products);

}
