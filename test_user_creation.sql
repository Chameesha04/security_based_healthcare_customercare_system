-- Test script to verify user creation in database
-- This script helps you verify that users are properly saved to the database

-- 1. Check all users in the database
SELECT 
    email,
    first_name,
    last_name,
    phone_number,
    role,
    CASE 
        WHEN LENGTH(password) > 20 THEN 'HASHED'
        WHEN password LIKE '<CHANGE_ME>' OR password LIKE '<CHANGE_ME>' THEN 'PLAIN_TEXT'
        ELSE 'UNKNOWN_FORMAT'
    END as password_format,
    LENGTH(password) as password_length
FROM users 
ORDER BY role, email;

-- 2. Count users by role
SELECT 
    role,
    COUNT(*) as user_count
FROM users 
GROUP BY role
ORDER BY role;

-- 3. Check for duplicate emails
SELECT 
    email,
    COUNT(*) as duplicate_count
FROM users 
GROUP BY email
HAVING COUNT(*) > 1;

-- 4. Test query to verify a specific user exists
-- Replace 'test@example.com' with the email you want to check
/*
SELECT 
    email,
    first_name,
    last_name,
    role,
    CASE 
        WHEN LENGTH(password) > 20 THEN 'HASHED'
        ELSE 'PLAIN_TEXT'
    END as password_format
FROM users 
WHERE email = 'test@example.com';
*/

-- 5. Check recent users (if you have a created_at timestamp)
-- This would show the most recently created users
/*
SELECT TOP 10
    email,
    first_name,
    last_name,
    role,
    created_at
FROM users 
ORDER BY created_at DESC;
*/
