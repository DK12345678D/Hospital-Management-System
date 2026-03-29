package app.hospital.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "audit_logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objectId;
    private String actorRole;
    private String actorName;
    private String module;
    
    @Column(columnDefinition = "TEXT")
    private String message;
    
    private String origin;
    private String action;
    
    private LocalDateTime timestamp = LocalDateTime.now();

    public AuditLog(String objectId, String actorRole, String actorName, String module, String message, String origin, String action) {
        this.objectId = objectId;
        this.actorRole = actorRole;
        this.actorName = actorName;
        this.module = module;
        this.message = message;
        this.origin = origin;
        this.action = action;
        this.timestamp = LocalDateTime.now();
    }
}
