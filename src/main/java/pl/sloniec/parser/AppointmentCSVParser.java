package pl.sloniec.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import pl.sloniec.domain.Appointment;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AppointmentCSVParser extends CSVParser<Appointment> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

    protected Appointment parse(CSVRecord csvRecord) {
        return Appointment.builder()
                .id(UUID.fromString(csvRecord.get(Headers.ID.getValue())))
                .clientId(UUID.fromString(csvRecord.get(Headers.CLIENT_ID.getValue())))
                .startTime(parseDateTime(csvRecord.get(Headers.START_TIME.getValue())))
                .endTime(parseDateTime(csvRecord.get(Headers.END_TIME.getValue())))
                .build();
    }

    @RequiredArgsConstructor
    @Getter
    private enum Headers {
        ID("id"),
        CLIENT_ID("client_id"),
        START_TIME("start_time"),
        END_TIME("end_time");

        private final String value;
    }

    private OffsetDateTime parseDateTime(String dateTimeString) {
        return OffsetDateTime.parse(dateTimeString, FORMATTER);
    }

}
