package pl.sloniec.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Appointment {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Client.class, fetch = FetchType.EAGER, optional = false)
    private Client client;

    @Column(name = "client_id", columnDefinition = "UUID")
    private UUID clientId;

    @NotNull
    @Column(nullable = false)
    private OffsetDateTime startTime;

    @NotNull
    @Column(nullable = false)
    private OffsetDateTime endTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "appointment")
    private List<Purchase> purchases;

    @PrePersist
    protected void onCreate() {
        if (isNull(this.id)) {
            this.id = UUID.randomUUID();
        }
    }
}
