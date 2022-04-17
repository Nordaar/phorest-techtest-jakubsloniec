package pl.sloniec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.sloniec.domain.Appointment;
import pl.sloniec.repository.AppointmentRepository;
import pl.sloniec.service.parser.AppointmentCSVParser;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentCSVParser appointmentCSVParser;

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment getById(UUID id) {
        return appointmentRepository.getById(id);
    }

    public Appointment create(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void create(List<Appointment> appointments) {
        appointmentRepository.saveAll(appointments);
    }

    public void importFromCSV(MultipartFile file) {
        List<Appointment> appointments = appointmentCSVParser.parse(file);
        appointmentRepository.saveAll(appointments);
    }
}