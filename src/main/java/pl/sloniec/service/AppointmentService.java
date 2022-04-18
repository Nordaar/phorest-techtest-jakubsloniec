package pl.sloniec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sloniec.domain.Appointment;
import pl.sloniec.repository.AppointmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> findById(UUID id) {
        return appointmentRepository.findById(id);
    }

    public Appointment create(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void create(List<Appointment> appointments) {
        appointmentRepository.saveAll(appointments);
    }
}
