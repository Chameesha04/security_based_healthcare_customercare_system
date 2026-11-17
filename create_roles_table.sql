-- Create roles table for role management system
-- This script creates the roles table with all necessary columns and constraints

-- Create roles table
CREATE TABLE roles (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE,
    display_name NVARCHAR(150) NOT NULL,
    description NVARCHAR(500),
    permissions NVARCHAR(MAX), -- JSON string of permissions
    is_active BIT NOT NULL DEFAULT 1,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2
);

-- Create indexes for better performance
CREATE INDEX IX_roles_name ON roles(name);
CREATE INDEX IX_roles_is_active ON roles(is_active);
CREATE INDEX IX_roles_display_name ON roles(display_name);

-- Insert default roles
INSERT INTO roles (name, display_name, description, permissions, is_active) VALUES
('CUSTOMER', 'Customer', 'Patients who can book appointments and access their medical records', 
 '["book_appointments", "view_medical_reports", "submit_feedback", "create_support_tickets"]', 1),

('RECEPTIONIST', 'Receptionist', 'Front desk staff who manage appointments and patient registration', 
 '["manage_appointments", "register_patients", "view_room_availability", "update_patient_info"]', 1),

('STAFF_COORDINATOR', 'Staff Coordinator', 'Manages doctor schedules and staff assignments', 
 '["manage_doctor_schedules", "assign_staff", "view_system_reports", "manage_rooms"]', 1),

('CUSTOMER_SUPPORT_MANAGER', 'Customer Support Manager', 'Handles customer support tickets and feedback', 
 '["manage_support_tickets", "respond_to_feedback", "view_customer_issues", "generate_reports"]', 1),

('SENIOR_MEDICAL_OFFICER', 'Senior Medical Officer', 'Senior medical staff with access to patient history and reports', 
 '["view_patient_history", "access_medical_reports", "manage_recommendations", "review_cases"]', 1),

('ADMIN', 'System Admin', 'Full system access for user management and system administration', 
 '["manage_all_users", "assign_roles", "system_configuration", "full_access"]', 1);

-- Add trigger to automatically update updated_at timestamp
CREATE TRIGGER TR_roles_updated_at
ON roles
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE roles 
    SET updated_at = GETDATE()
    FROM roles r
    INNER JOIN inserted i ON r.id = i.id;
END;

-- Verify the table was created successfully
SELECT 
    'Roles table created successfully' AS status,
    COUNT(*) AS total_roles,
    SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS active_roles
FROM roles;
