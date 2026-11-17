package com.example.healthcare_customercare.service;
import com.example.healthcare_customercare.entity.AuditLog;
import com.example.healthcare_customercare.entity.User;
import com.example.healthcare_customercare.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    public void log(String action, User actor, String target, HttpServletRequest request, String details) {
        String email = actor != null ? actor.getEmail() : null;
        String role = actor != null ? actor.getRole() : null;
        String ip = request != null ? request.getRemoteAddr() : null;

        AuditLog auditLog = new AuditLog(email, role, action, target, ip, details);
        auditLogRepository.save(auditLog);
}
    public void logAnonymous(String action, String target, HttpServletRequest request, String details) {
        String ip = request != null ? request.getRemoteAddr() : null;

        AuditLog auditLog = new AuditLog(null, null, action, target, ip, details);
        auditLogRepository.save(auditLog);
    }
}
