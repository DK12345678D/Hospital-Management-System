package app.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "bills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private Double amount;

    private String status; // PAID, UNPAID, PARTIAL

    private String billType; // OPD, IPD, PHARMACY, LAB

    private LocalDateTime billDate;

    private String paymentMethod; // CASH, CARD, ONLINE
}
