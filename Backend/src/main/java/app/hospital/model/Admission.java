package app.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "admissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String roomNo;

    private String wardName;

    private LocalDateTime admissionDate;

    private LocalDateTime dischargeDate;

    private String reason;

    private String dischargeSummary;

    private String status; // ADMITTED, DISCHARGED
}
