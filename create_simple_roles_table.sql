-- Simple roles table with only role_id and role_name
-- Drop existing roles table if it exists
IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'roles')
BEGIN
    DROP TABLE roles;
    PRINT 'Existing roles table dropped';
END

-- Create simple roles table
CREATE TABLE roles (
    role_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    role_name NVARCHAR(100) NOT NULL UNIQUE
);

-- Insert default roles
INSERT INTO roles (role_name) VALUES
('CUSTOMER'),
('RECEPTIONIST'),
('STAFF_COORDINATOR'),
('CUSTOMER_SUPPORT_MANAGER'),
('SENIOR_MEDICAL_OFFICER'),
('ADMIN');

-- Verify the table was created successfully
SELECT 
    'Simple roles table created successfully' AS status,
    COUNT(*) AS total_roles
FROM roles;

-- Show the table structure
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'roles' 
ORDER BY ORDINAL_POSITION;
