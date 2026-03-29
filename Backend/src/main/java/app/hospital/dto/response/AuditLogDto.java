package app.hospital.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDto {
    private Long id;
    private String objectId;
    private String actorRole;
    private String actorName;
    private String module;
    private String message;
    private String origin;
    private String action;
    private LocalDateTime timestamp;
}
