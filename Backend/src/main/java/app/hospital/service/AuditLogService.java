package app.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import app.hospital.model.AuditLog;
import app.hospital.repository.AuditLogRepository;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void log(String objectId, String actorRole, String actorName, String module, String message, String origin, String action) {
        AuditLog auditLog = new AuditLog(objectId, actorRole, actorName, module, message, origin, action);
        auditLogRepository.save(auditLog);
    }
}
