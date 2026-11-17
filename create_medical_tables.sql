-- Create medical_reports table
CREATE TABLE medical_reports (
    report_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    patient_email NVARCHAR(255) NOT NULL,
    report_type NVARCHAR(100) NOT NULL,
    report_date DATE NOT NULL,
    report_description NVARCHAR(MAX) NOT NULL,
    priority NVARCHAR(50) NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    created_by NVARCHAR(100)
);

-- Create medical_recommendations table
CREATE TABLE medical_recommendations (
    recommendation_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    patient_email NVARCHAR(255) NOT NULL,
    recommendation_type NVARCHAR(100) NOT NULL,
    recommendation_details NVARCHAR(MAX) NOT NULL,
    recommendation_text NVARCHAR(MAX) NOT NULL,
    priority NVARCHAR(50) NOT NULL,
    follow_up_date DATE,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    created_by NVARCHAR(100) NOT NULL
);

-- Add indexes for better performance
CREATE INDEX IX_medical_reports_patient_email ON medical_reports(patient_email);
CREATE INDEX IX_medical_reports_priority ON medical_reports(priority);
CREATE INDEX IX_medical_reports_created_at ON medical_reports(created_at);

CREATE INDEX IX_medical_recommendations_patient_email ON medical_recommendations(patient_email);
CREATE INDEX IX_medical_recommendations_priority ON medical_recommendations(priority);
CREATE INDEX IX_medical_recommendations_created_at ON medical_recommendations(created_at);
