-- Check existing admin users and their password format
-- This script helps identify if admin users have plain text passwords

-- First, let's see what admin users exist
SELECT email, first_name, last_name, role, 
       CASE 
           WHEN password LIKE '<CHANGE_ME>' THEN 'PLAIN_TEXT'
           WHEN LENGTH(password) > 20 THEN 'HASHED'
           ELSE 'UNKNOWN_FORMAT'
       END as password_format
FROM users 
WHERE role = 'ADMIN';

-- If you find admin users with plain text passwords, you can update them
-- Uncomment the following lines and replace 'admin@arogya.com' with the actual email:

-- UPDATE users 
-- SET password = 'HASHED_PASSWORD_HERE'  -- Replace with actual hashed password
-- WHERE email = 'admin@arogya.com' AND role = 'ADMIN';

-- To create a new admin user with proper hashing, use the application
-- The DataInitializer will now create admin users with properly hashed passwords
