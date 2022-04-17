package pl.sloniec.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Purchase {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @JoinColumn(name = "appointment_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Appointment.class, fetch = FetchType.EAGER, optional = false)
    private Appointment appointment;

    @Column(name = "appointment_id", columnDefinition = "UUID")
    private UUID appointmentId;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(nullable = false)
    private Integer loyaltyPoints;
}
