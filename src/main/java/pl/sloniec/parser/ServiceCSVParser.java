package pl.sloniec.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sloniec.domain.ProductType;

@RequiredArgsConstructor
@Component
public class ServiceCSVParser extends ProductCSVParser {
    @Override
    protected ProductType getType() {
        return ProductType.SERVICE;
    }
}
