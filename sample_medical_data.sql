-- Sample Medical Data for Testing Patient History Feature
-- Run this in SQL Server Management Studio or your database client

-- Insert sample medical reports
INSERT INTO medical_reports (patient_email, report_type, report_date, report_description, priority, created_at, created_by)
VALUES 
('john.doe@example.com', 'Blood Test', '2024-01-15', 'Complete Blood Count (CBC) shows normal values. Hemoglobin: 14.2 g/dL, White Blood Cells: 7,500/μL, Platelets: 250,000/μL. All values within normal range.', 'low', GETDATE(), 'smo@arogya.com'),
('john.doe@example.com', 'X-Ray', '2024-01-20', 'Chest X-ray shows clear lung fields with no signs of pneumonia or other abnormalities. Heart size appears normal. No fractures or other injuries detected.', 'medium', GETDATE(), 'smo@arogya.com'),
('john.doe@example.com', 'MRI', '2024-02-01', 'Brain MRI shows no signs of tumors, bleeding, or other abnormalities. All structures appear normal. No evidence of stroke or other neurological issues.', 'high', GETDATE(), 'smo@arogya.com'),
('ama@gmail.com', 'Blood Test', '2024-01-10', 'Blood glucose levels elevated at 180 mg/dL. Hemoglobin A1c: 8.2%. Patient shows signs of diabetes. Further testing recommended.', 'urgent', GETDATE(), 'smo@arogya.com'),
('ama@gmail.com', 'ECG', '2024-01-12', 'Electrocardiogram shows normal sinus rhythm. No signs of arrhythmia or heart disease. Blood pressure readings normal.', 'low', GETDATE(), 'smo@arogya.com');

-- Insert sample medical recommendations
INSERT INTO medical_recommendations (patient_email, recommendation_type, recommendation_details, recommendation_text, priority, follow_up_date, created_at, created_by)
VALUES 
('john.doe@example.com', 'Lifestyle', 'Regular exercise routine and balanced diet recommended. Patient should engage in at least 30 minutes of moderate exercise daily and maintain a diet rich in fruits, vegetables, and lean proteins.', 'Regular exercise routine and balanced diet recommended. Patient should engage in at least 30 minutes of moderate exercise daily and maintain a diet rich in fruits, vegetables, and lean proteins.', 'medium', '2024-03-15', GETDATE(), 'smo@arogya.com'),
('john.doe@example.com', 'Follow-up', 'Schedule follow-up appointment in 3 months to monitor overall health status and review any concerns. Patient should contact clinic if any new symptoms develop.', 'Schedule follow-up appointment in 3 months to monitor overall health status and review any concerns. Patient should contact clinic if any new symptoms develop.', 'low', '2024-04-15', GETDATE(), 'smo@arogya.com'),
('ama@gmail.com', 'Medication', 'Prescribe Metformin 500mg twice daily with meals. Patient should monitor blood glucose levels regularly and report any side effects. Dietary counseling recommended.', 'Prescribe Metformin 500mg twice daily with meals. Patient should monitor blood glucose levels regularly and report any side effects. Dietary counseling recommended.', 'urgent', '2024-02-15', GETDATE(), 'smo@arogya.com'),
('ama@gmail.com', 'Diet', 'Low-carbohydrate diet recommended. Patient should limit sugar intake, avoid processed foods, and focus on whole grains, vegetables, and lean proteins. Regular meal timing important.', 'Low-carbohydrate diet recommended. Patient should limit sugar intake, avoid processed foods, and focus on whole grains, vegetables, and lean proteins. Regular meal timing important.', 'high', '2024-02-20', GETDATE(), 'smo@arogya.com'),
('ama@gmail.com', 'Exercise', 'Start with light walking 20 minutes daily, gradually increasing to 30 minutes. Avoid high-intensity exercises initially. Monitor blood glucose before and after exercise.', 'Start with light walking 20 minutes daily, gradually increasing to 30 minutes. Avoid high-intensity exercises initially. Monitor blood glucose before and after exercise.', 'medium', '2024-02-25', GETDATE(), 'smo@arogya.com');

-- Verify the data was inserted
SELECT 'Medical Reports' as Table_Name, COUNT(*) as Record_Count FROM medical_reports
UNION ALL
SELECT 'Medical Recommendations' as Table_Name, COUNT(*) as Record_Count FROM medical_recommendations;
