IF COL_LENGTH('tickets', 'deleted_by_customer') IS NULL
    ALTER TABLE tickets ADD deleted_by_customer BIT NULL DEFAULT 0;

IF COL_LENGTH('tickets', 'deleted_by_manager') IS NULL
    ALTER TABLE tickets ADD deleted_by_manager BIT NULL DEFAULT 0;

IF COL_LENGTH('tickets', 'deleted_at') IS NULL
    ALTER TABLE tickets ADD deleted_at DATETIME2 NULL;

IF COL_LENGTH('tickets', 'deleted_by_email') IS NULL
    ALTER TABLE tickets ADD deleted_by_email NVARCHAR(255) NULL;

IF COL_LENGTH('feedback', 'deleted_by_customer') IS NULL
    ALTER TABLE feedback ADD deleted_by_customer BIT NULL DEFAULT 0;

IF COL_LENGTH('feedback', 'deleted_by_manager') IS NULL
    ALTER TABLE feedback ADD deleted_by_manager BIT NULL DEFAULT 0;

IF COL_LENGTH('feedback', 'deleted_at') IS NULL
    ALTER TABLE feedback ADD deleted_at DATETIME2 NULL;

IF COL_LENGTH('feedback', 'deleted_by_email') IS NULL
    ALTER TABLE feedback ADD deleted_by_email NVARCHAR(255) NULL;
