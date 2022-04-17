package pl.sloniec.delegate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.sloniec.controller.AppointmentsApiDelegate;
import pl.sloniec.domain.Appointment;
import pl.sloniec.dto.AppointmentDTO;
import pl.sloniec.mapper.AppointmentMapper;
import pl.sloniec.mapper.AppointmentMapperImpl;
import pl.sloniec.service.AppointmentService;
import pl.sloniec.service.parser.AppointmentCSVParser;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AppointmentsDelegate implements AppointmentsApiDelegate {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentCSVParser appointmentCSVParser;

    public static void main(String[] args) {
        AppointmentDTO dto = new AppointmentDTO()
                .id(UUID.randomUUID())
                .clientId(UUID.randomUUID())
                .startTime(OffsetDateTime.now().minus(1, ChronoUnit.DAYS))
                .endTime(OffsetDateTime.now());

        System.out.println(dto);

        AppointmentMapper mapper = new AppointmentMapperImpl();
        Appointment appointment = mapper.fromDTO(dto);
        System.out.println(appointment);

    }

    @Override
    public ResponseEntity<List<AppointmentDTO>> listAppointments() {
        return ResponseEntity.ok(appointmentMapper.toDTO(appointmentService.findAll()));
    }

    @Override
    public ResponseEntity<AppointmentDTO> showAppointmentById(String appointmentId) {
        try {
            Appointment appointment = appointmentService.getById(UUID.fromString(appointmentId));
            return ResponseEntity.ok(appointmentMapper.toDTO(appointment));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<AppointmentDTO> createAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment1 = appointmentMapper.fromDTO(appointmentDTO);
        Appointment appointment = appointmentService.create(appointment1);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentMapper.toDTO(appointment));
    }


    @Override
    public ResponseEntity<Void> importAppointments(MultipartFile file) {
        List<Appointment> appointments = appointmentCSVParser.parse(file);
        appointmentService.create(appointments);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
