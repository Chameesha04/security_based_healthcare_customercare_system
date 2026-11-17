-- Debug script to check notifications in the database
-- Run these queries to see what's in your notifications table

-- 1. Check if the notifications table exists
SELECT TABLE_NAME 
FROM INFORMATION_SCHEMA.TABLES 
WHERE TABLE_NAME = 'notifications';

-- 2. Check the structure of the notifications table
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'notifications'
ORDER BY ORDINAL_POSITION;

-- 3. Count total notifications
SELECT COUNT(*) as TotalNotifications FROM notifications;

-- 4. Count notifications by user
SELECT user_email, COUNT(*) as NotificationCount 
FROM notifications 
GROUP BY user_email;

-- 5. Show all notifications (first 10)
SELECT TOP 10 
    notification_id,
    user_email,
    title,
    message,
    type,
    priority,
    is_read,
    created_at
FROM notifications 
ORDER BY created_at DESC;

-- 6. Show notifications for customer@example.com specifically
SELECT 
    notification_id,
    user_email,
    title,
    message,
    type,
    priority,
    is_read,
    created_at
FROM notifications 
WHERE user_email = 'customer@example.com'
ORDER BY created_at DESC;

-- 7. Check for any notifications with different email formats
SELECT DISTINCT user_email FROM notifications;

-- 8. Insert a test notification if none exist
INSERT INTO notifications (user_email, title, message, type, priority, is_read, created_at)
SELECT 'customer@example.com', 'Test Notification', 'This is a test notification', 'SYSTEM', 'MEDIUM', 0, GETDATE()
WHERE NOT EXISTS (
    SELECT 1 FROM notifications WHERE user_email = 'customer@example.com'
);

