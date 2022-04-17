package pl.sloniec.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import pl.sloniec.domain.Purchase;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PurchaseCSVParser extends CSVParser<Purchase> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

    protected Purchase parse(CSVRecord csvRecord) {
        return Purchase.builder()
                .id(UUID.fromString(csvRecord.get(Headers.ID.getValue())))
                .appointmentId(UUID.fromString(csvRecord.get(Headers.APPOINTMENT_ID.getValue())))
                .name(csvRecord.get(Headers.NAME.getValue()))
                .price(BigDecimal.valueOf(Double.parseDouble(csvRecord.get(Headers.PRICE.getValue()))))
                .loyaltyPoints(Integer.valueOf(csvRecord.get(Headers.LOYALTY_POINTS.getValue())))
                .build();
    }

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

    private OffsetDateTime parseDateTime(String dateTimeString) {
        return OffsetDateTime.parse(dateTimeString, FORMATTER);
    }

}
