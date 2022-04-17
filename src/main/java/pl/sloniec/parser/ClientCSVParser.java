package pl.sloniec.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import pl.sloniec.domain.Client;
import pl.sloniec.domain.Gender;

import java.util.UUID;

@Component
public class ClientCSVParser extends CSVParser<Client> {

    @Override
    protected Client parse(CSVRecord csvRecord) {
        return Client.builder()
                .id(UUID.fromString(csvRecord.get(Headers.ID.getValue())))
                .firstName(csvRecord.get(Headers.FIRST_NAME.getValue()))
                .lastName(csvRecord.get(Headers.LAST_NAME.getValue()))
                .email(csvRecord.get(Headers.EMAIL.getValue()))
                .phone(csvRecord.get(Headers.PHONE.getValue()))
                .gender(Gender.fromValue(csvRecord.get(Headers.GENDER.getValue())))
                .banned(Boolean.valueOf(csvRecord.get(Headers.BANNED.getValue())))
                .build();
    }

    @RequiredArgsConstructor
    @Getter
    private enum Headers {
        ID("id"),
        FIRST_NAME("first_name"),
        LAST_NAME("last_name"),
        EMAIL("email"),
        PHONE("phone"),
        GENDER("gender"),
        BANNED("banned");

        private final String value;
    }
}
