package pl.sloniec.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Client {

    @Id
    private UUID id;

    @Column(
            columnDefinition = "text",
            nullable = false
    )
    private String firstName;

    @Column(
            columnDefinition = "text",
            nullable = false
    )
    private String lastName;

    @Column(
            columnDefinition = "text",
            nullable = false
    )
    private String email;

    @Column(
            columnDefinition = "text",
            nullable = false
    )
    private String phone;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)

    private Boolean banned;
}
