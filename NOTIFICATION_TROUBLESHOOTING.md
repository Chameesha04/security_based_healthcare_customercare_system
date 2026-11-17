# 🔔 Notification System Troubleshooting Guide

## 🚨 **Issue: Notifications not showing in Customer UI**

### **Step 1: Quick Database Check**
Run this SQL to see what's in your database:
```sql
-- Check if notifications table exists
SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'notifications';

-- Check notifications for customer@example.com
SELECT * FROM notifications WHERE user_email = 'customer@example.com';

-- Count total notifications
SELECT COUNT(*) as TotalNotifications FROM notifications;
```

### **Step 2: Use Debug Tools**
1. **Start your application**: `mvn spring-boot:run`
2. **Open debug page**: `http://localhost:8080/debug-notifications.html`
3. **Run Full Diagnostic** - This will test everything automatically

### **Step 3: Test API Directly**
1. **Open simple test**: `http://localhost:8080/simple-notification-test.html`
2. **Click "Create Test Notification"**
3. **Click "Load Notifications"**
4. **Check if notifications appear**

### **Step 4: Check Browser Console**
1. **Open Customer Dashboard**: `http://localhost:8080/customer.html`
2. **Press F12** to open DevTools
3. **Go to Console tab**
4. **Click the notification bell icon**
5. **Look for error messages**

## 🔧 **Common Issues & Solutions**

### **Issue 1: Database Table Missing**
**Symptoms**: API returns 500 error
**Solution**: Run this SQL:
```sql
CREATE TABLE notifications (
    notification_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_email NVARCHAR(255) NOT NULL,
    title NVARCHAR(255) NOT NULL,
    message NVARCHAR(MAX) NOT NULL,
    type NVARCHAR(50) NOT NULL,
    priority NVARCHAR(20) NOT NULL,
    is_read BIT NOT NULL DEFAULT 0,
    related_entity_id BIGINT,
    related_entity_type NVARCHAR(50),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    expires_at DATETIME2
);
```

### **Issue 2: No Notifications in Database**
**Symptoms**: API returns empty array `[]`
**Solution**: Create test notifications:
```sql
INSERT INTO notifications (user_email, title, message, type, priority, is_read, created_at)
VALUES 
('customer@example.com', 'Test Notification 1', 'This is a test notification', 'SYSTEM', 'MEDIUM', 0, GETDATE()),
('customer@example.com', 'Appointment Reminder', 'Your appointment is tomorrow', 'APPOINTMENT', 'HIGH', 0, GETDATE()),
('customer@example.com', 'Medical Report Ready', 'Your lab results are available', 'MEDICAL_REPORT', 'HIGH', 0, GETDATE());
```

### **Issue 3: Email Mismatch**
**Symptoms**: Notifications exist but for different email
**Solution**: Check what emails exist:
```sql
SELECT DISTINCT user_email FROM notifications;
```
Then update the JavaScript in customer.html:
```javascript
let currentUserEmail = 'your-actual-email@example.com';
```

### **Issue 4: Expired Notifications**
**Symptoms**: Notifications exist but API returns empty array
**Solution**: Check if notifications have expiration dates:
```sql
SELECT user_email, title, expires_at, created_at 
FROM notifications 
WHERE user_email = 'customer@example.com';
```
If `expires_at` is set to a past date, the API filters them out.

### **Issue 5: Application Not Running**
**Symptoms**: All API calls fail with network errors
**Solution**: 
1. Check if application is running: `http://localhost:8080`
2. Start application: `mvn spring-boot:run`
3. Check console for errors

### **Issue 6: CORS Issues**
**Symptoms**: Browser console shows CORS errors
**Solution**: The controller already has `@CrossOrigin(origins = "*")` but check if there are any proxy issues.

## 🧪 **Testing Steps**

### **Test 1: API Endpoints**
```bash
# Test getting notifications
curl http://localhost:8080/api/notifications/user/customer@example.com

# Test getting count
curl http://localhost:8080/api/notifications/user/customer@example.com/count

# Test creating notification
curl -X POST http://localhost:8080/api/notifications/test \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "userEmail=customer@example.com&title=Test&message=Test message&type=SYSTEM&priority=MEDIUM"
```

### **Test 2: Database Direct**
```sql
-- Insert test notification
INSERT INTO notifications (user_email, title, message, type, priority, is_read, created_at)
VALUES ('customer@example.com', 'Direct DB Test', 'Created directly in database', 'SYSTEM', 'MEDIUM', 0, GETDATE());

-- Check if it was created
SELECT * FROM notifications WHERE user_email = 'customer@example.com';
```

### **Test 3: Customer UI**
1. Open `http://localhost:8080/customer.html`
2. Open browser DevTools (F12)
3. Go to Console tab
4. Click notification bell
5. Look for console messages

## 🎯 **Expected Behavior**

### **When Working Correctly:**
1. **API Response**: `[{"notificationId":1,"userEmail":"customer@example.com",...}]`
2. **Browser Console**: Shows loading messages and notification data
3. **UI**: Notifications appear in dropdown with proper styling
4. **Badge**: Shows unread count

### **When Not Working:**
1. **API Response**: `[]` (empty array) or error
2. **Browser Console**: Shows error messages
3. **UI**: Shows "Loading notifications..." or error message
4. **Badge**: Hidden or shows 0

## 🚀 **Quick Fix Commands**

```bash
# 1. Start application
mvn spring-boot:run

# 2. Create database table (run in SQL Server)
# Use the SQL from Issue 1 above

# 3. Insert test data (run in SQL Server)
# Use the SQL from Issue 2 above

# 4. Test API
curl http://localhost:8080/api/notifications/user/customer@example.com

# 5. Open debug page
# Navigate to: http://localhost:8080/debug-notifications.html
```

## 📞 **Still Not Working?**

If you're still having issues, please share:
1. **Console output** from the debug page
2. **Database query results** from the SQL commands above
3. **Browser console errors** from customer.html
4. **Application logs** from your Spring Boot console

This will help identify the exact issue!

