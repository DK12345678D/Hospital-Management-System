package app.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "lab_tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;

    private String category; // BLOOD, URINE, SCAN, X-RAY

    private Double price;

    private String normalRange;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String result;

    private LocalDateTime testDate;

    private String status; // PENDING, COMPLETED
}
