-- Create notifications table
CREATE TABLE notifications (
    notification_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_email NVARCHAR(255) NOT NULL,
    title NVARCHAR(255) NOT NULL,
    message NVARCHAR(MAX) NOT NULL,
    type NVARCHAR(50) NOT NULL, -- APPOINTMENT, MEDICAL_REPORT, RECOMMENDATION, SUPPORT_TICKET, SYSTEM
    priority NVARCHAR(20) NOT NULL, -- LOW, MEDIUM, HIGH, URGENT
    is_read BIT NOT NULL DEFAULT 0,
    related_entity_id BIGINT, -- ID of the related entity (appointment, report, etc.)
    related_entity_type NVARCHAR(50), -- Type of related entity
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    expires_at DATETIME2 -- Optional expiration date
);

-- Add indexes for better performance
CREATE INDEX IX_notifications_user_email ON notifications(user_email);
CREATE INDEX IX_notifications_type ON notifications(type);
CREATE INDEX IX_notifications_priority ON notifications(priority);
CREATE INDEX IX_notifications_is_read ON notifications(is_read);
CREATE INDEX IX_notifications_created_at ON notifications(created_at);
CREATE INDEX IX_notifications_related_entity ON notifications(related_entity_id, related_entity_type);

-- Add check constraints
ALTER TABLE notifications ADD CONSTRAINT CK_notifications_type 
    CHECK (type IN ('APPOINTMENT', 'MEDICAL_REPORT', 'RECOMMENDATION', 'SUPPORT_TICKET', 'SYSTEM'));

ALTER TABLE notifications ADD CONSTRAINT CK_notifications_priority 
    CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'URGENT'));
