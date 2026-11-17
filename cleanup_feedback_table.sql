-- SQL Script to clean up feedback table
-- Run this script in your SQL Server Management Studio or database client

-- First, let's see what columns currently exist
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'feedback'
ORDER BY ORDINAL_POSITION;

-- Drop unnecessary columns (run these one by one to avoid errors)
-- Note: Some columns might not exist, so you may get "Column does not exist" errors - that's okay

-- Drop columns that are not needed
IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'feedback' AND COLUMN_NAME = 'comment')
    ALTER TABLE feedback DROP COLUMN comment;

IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'feedback' AND COLUMN_NAME = 'patient_name')
    ALTER TABLE feedback DROP COLUMN patient_name;

IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'feedback' AND COLUMN_NAME = 'feedback_status')
    ALTER TABLE feedback DROP COLUMN feedback_status;

IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'feedback' AND COLUMN_NAME = 'updated_at')
    ALTER TABLE feedback DROP COLUMN updated_at;

IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'feedback' AND COLUMN_NAME = 'doctor_id')
    ALTER TABLE feedback DROP COLUMN doctor_id;

IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'feedback' AND COLUMN_NAME = 'feedback_message')
    ALTER TABLE feedback DROP COLUMN feedback_message;

IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'feedback' AND COLUMN_NAME = 'status')
    ALTER TABLE feedback DROP COLUMN status;

IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'feedback' AND COLUMN_NAME = 'submission_date')
    ALTER TABLE feedback DROP COLUMN submission_date;

-- Check the final structure after cleanup
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'feedback'
ORDER BY ORDINAL_POSITION;

-- If you need to recreate the table completely, use this:
/*
DROP TABLE IF EXISTS feedback;

CREATE TABLE feedback (
    feedback_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    created_at DATETIME2(6) NOT NULL DEFAULT GETDATE(),
    feedback_type VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    rating INT NOT NULL,
    user_email VARCHAR(255) NOT NULL
);
*/
