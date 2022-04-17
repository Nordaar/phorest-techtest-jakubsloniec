package pl.sloniec.mapper;

import org.mapstruct.Mapper;
import pl.sloniec.domain.Appointment;
import pl.sloniec.dto.AppointmentDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    Appointment fromDTO(AppointmentDTO appointmentDTO);

    List<Appointment> fromDTO(List<AppointmentDTO> appointmentDTOs);

    AppointmentDTO toDTO(Appointment appointment);

    List<AppointmentDTO> toDTO(List<Appointment> appointments);

}
