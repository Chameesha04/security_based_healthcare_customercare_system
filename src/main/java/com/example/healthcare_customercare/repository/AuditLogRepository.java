package com.example.healthcare_customercare.repository;
import com.example.healthcare_customercare.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
