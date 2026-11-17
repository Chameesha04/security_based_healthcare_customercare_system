-- Manual SQL Script to Clean Up Feedback Table
-- Run this in SQL Server Management Studio or your database client

-- Step 1: Check current table structure
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'feedback'
ORDER BY ORDINAL_POSITION;

-- Step 2: Drop unnecessary columns (run these commands one by one)
-- If a column doesn't exist, you'll get an error - that's okay, just continue

ALTER TABLE feedback DROP COLUMN comment;
ALTER TABLE feedback DROP COLUMN patient_name;
ALTER TABLE feedback DROP COLUMN feedback_status;
ALTER TABLE feedback DROP COLUMN updated_at;
ALTER TABLE feedback DROP COLUMN doctor_id;
ALTER TABLE feedback DROP COLUMN feedback_message;
ALTER TABLE feedback DROP COLUMN status;
ALTER TABLE feedback DROP COLUMN submission_date;

-- Step 3: Verify the final structure
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'feedback'
ORDER BY ORDINAL_POSITION;

-- Step 4: If you want to completely recreate the table (this will delete all data)
/*
DROP TABLE feedback;

CREATE TABLE feedback (
    feedback_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    created_at DATETIME2(6) NOT NULL DEFAULT GETDATE(),
    feedback_type VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    rating INT NOT NULL,
    user_email VARCHAR(255) NOT NULL
);
*/
