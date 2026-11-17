-- CRITICAL SECURITY CHECK: Notification Privacy Investigation
-- Run these queries to investigate the privacy issue

-- 1. Check what users have notifications
SELECT 
    user_email, 
    COUNT(*) as notification_count,
    MIN(created_at) as first_notification,
    MAX(created_at) as last_notification
FROM notifications 
GROUP BY user_email 
ORDER BY notification_count DESC;

-- 2. Check recent notifications (last 10)
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

-- 3. Check if there are notifications for multiple users
SELECT 
    COUNT(DISTINCT user_email) as unique_users_with_notifications,
    COUNT(*) as total_notifications
FROM notifications;

-- 4. Check notifications for specific users (replace with actual emails)
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
WHERE user_email IN ('bs@gmail.com', 'customer@example.com', 'test@example.com')
ORDER BY user_email, created_at DESC;

-- 5. Check for any notifications with NULL or empty user_email
SELECT 
    notification_id,
    user_email,
    title,
    message,
    created_at
FROM notifications 
WHERE user_email IS NULL OR user_email = '' OR user_email = 'null';

-- 6. Check notification distribution by type
SELECT 
    type,
    COUNT(*) as count,
    COUNT(DISTINCT user_email) as unique_users
FROM notifications 
GROUP BY type
ORDER BY count DESC;

-- 7. Check if notifications are being created for wrong users
-- This query will help identify if there's a pattern
SELECT 
    user_email,
    type,
    COUNT(*) as count,
    STRING_AGG(title, '; ') as sample_titles
FROM notifications 
GROUP BY user_email, type
ORDER BY user_email, count DESC;

-- 8. Emergency: Delete test notifications if needed
-- WARNING: Only run this if you want to clear test data
/*
DELETE FROM notifications 
WHERE user_email IN ('customer@example.com', 'test@example.com')
AND title LIKE '%Test%' OR title LIKE '%Debug%';
*/

-- 9. Check for duplicate notifications
SELECT 
    user_email,
    title,
    message,
    COUNT(*) as duplicate_count
FROM notifications 
GROUP BY user_email, title, message
HAVING COUNT(*) > 1
ORDER BY duplicate_count DESC;

-- 10. Check notification creation pattern
SELECT 
    user_email,
    COUNT(*) as total_notifications,
    SUM(CASE WHEN is_read = 1 THEN 1 ELSE 0 END) as read_notifications,
    SUM(CASE WHEN is_read = 0 THEN 1 ELSE 0 END) as unread_notifications
FROM notifications 
GROUP BY user_email
ORDER BY total_notifications DESC;

