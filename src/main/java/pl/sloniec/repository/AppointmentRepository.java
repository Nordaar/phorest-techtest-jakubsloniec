package pl.sloniec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sloniec.domain.Appointment;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
}
