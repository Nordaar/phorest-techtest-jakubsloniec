package pl.sloniec.service.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.sloniec.domain.Client;
import pl.sloniec.domain.Gender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ClientCSVParser {

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

    public List<Client> parse(MultipartFile file) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
//                     CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord().withIgnoreHeaderCase().withTrim()
                     CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).build()
             )
        ) {
            return csvParser.getRecords().stream()
                    .map(this::toClient)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private Client toClient(CSVRecord csvRecord) {
        return Client.builder()
                .id(UUID.fromString(csvRecord.get(Headers.ID.getValue())))
                .firstName(csvRecord.get(Headers.FIRST_NAME.getValue()))
                .lastName(csvRecord.get(Headers.LAST_NAME.getValue()))
                .email(csvRecord.get(Headers.EMAIL.getValue()))
                .phone(csvRecord.get(Headers.PHONE.getValue()))
                .gender(Gender.valueOf(csvRecord.get(Headers.GENDER.getValue())))
                .banned(Boolean.valueOf(csvRecord.get(Headers.BANNED.getValue())))
                .build();
    }
}
