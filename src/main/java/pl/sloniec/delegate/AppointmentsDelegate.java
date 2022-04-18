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
import pl.sloniec.parser.AppointmentCSVParser;
import pl.sloniec.service.AppointmentService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AppointmentsDelegate implements AppointmentsApiDelegate {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentCSVParser appointmentCSVParser;

    @Override
    public ResponseEntity<List<AppointmentDTO>> listAppointments() {
        return ResponseEntity.ok(appointmentMapper.toDTO(appointmentService.findAll()));
    }

    @Override
    public ResponseEntity<AppointmentDTO> showAppointmentById(UUID appointmentId) {
        return appointmentService.findById(appointmentId)
                .map(appointment -> ResponseEntity.ok(appointmentMapper.toDTO(appointment)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<AppointmentDTO> createAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentService.create(appointmentMapper.fromDTO(appointmentDTO));
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
