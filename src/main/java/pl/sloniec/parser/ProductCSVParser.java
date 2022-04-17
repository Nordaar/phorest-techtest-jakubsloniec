package pl.sloniec.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import pl.sloniec.domain.Product;
import pl.sloniec.domain.ProductType;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class ProductCSVParser extends CSVParser<Product> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

    protected Product parse(CSVRecord csvRecord) {
        return Product.builder()
                .id(UUID.fromString(csvRecord.get(Headers.ID.getValue())))
                .appointmentId(UUID.fromString(csvRecord.get(Headers.APPOINTMENT_ID.getValue())))
                .name(csvRecord.get(Headers.NAME.getValue()))
                .price(BigDecimal.valueOf(Double.parseDouble(csvRecord.get(Headers.PRICE.getValue()))))
                .loyaltyPoints(Integer.valueOf(csvRecord.get(Headers.LOYALTY_POINTS.getValue())))
                .productType(getType())
                .build();
    }

    protected abstract ProductType getType();

    @RequiredArgsConstructor
    @Getter
    private enum Headers {
        ID("id"),
        APPOINTMENT_ID("appointment_id"),
        NAME("name"),
        PRICE("price"),
        LOYALTY_POINTS("loyalty_points");

        private final String value;
    }
}
