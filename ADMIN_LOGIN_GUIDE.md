# Admin Login Testing Guide

## Issue Fixed
The admin login was failing because the admin user was created with a plain text password, but the authentication system expects hashed passwords.

## Changes Made

### 1. Fixed Password Hashing in DataInitializer
- Updated `DataInitializer.java` to use `userService.hashPassword("<CHANGE_ME>")` instead of plain text
- Made `hashPassword` method public in `UserService.java`

### 2. Added Test Endpoint
- Added `/system-admin/test-admin-login` endpoint to verify admin user exists

## Testing Steps

### Step 1: Restart the Application
```bash
# Stop the current application (Ctrl+C in the terminal where it's running)
# Then restart it
mvn spring-boot:run
```

### Step 2: Verify Admin User Creation
When the application starts, you should see this message in the console:
```
System Admin user created!
Email: admin@arogya.com
Password: <CHANGE_ME>
```

### Step 3: Test Admin Login
1. Open your browser and go to: `http://localhost:8080/login.html`
2. Enter the following credentials:
   - **Email:** `admin@arogya.com`
   - **Password:** `<CHANGE_ME>`
3. Click "Login"

### Step 4: Expected Result
- You should be redirected to: `http://localhost:8080/system-admin/dashboard?success=login_successful&email=admin@arogya.com`
- You should see the System Admin dashboard with user management features

### Step 5: Test Admin User Exists (Optional)
You can also test if the admin user exists by visiting:
```
http://localhost:8080/system-admin/test-admin-login?email=admin@arogya.com&password=<CHANGE_ME>
```

## Troubleshooting

### If Login Still Fails:

1. **Check Database**: Run the SQL script `check_admin_users.sql` to see if admin users exist and their password format

2. **Clear Database**: If you have old admin users with plain text passwords, you may need to:
   ```sql
   DELETE FROM users WHERE role = 'ADMIN';
   ```
   Then restart the application to create a new admin user with proper password hashing

3. **Check Console Logs**: Look for these debug messages during login:
   ```
   === LOGIN DEBUG ===
   === USER SERVICE AUTH DEBUG ===
   === PASSWORD VERIFICATION DEBUG ===
   ```

4. **Verify User Creation**: Check if the admin user was created by visiting:
   ```
   http://localhost:8080/system-admin/api/admin/users
   ```

## Admin Dashboard Features

Once logged in, you can:
- **Add New Users**: Create users with different roles
- **Manage Users**: View, edit, delete users
- **View Statistics**: See user counts and system metrics
- **Role Management**: Understand different user roles and permissions

## Default Admin Credentials
- **Email:** `admin@arogya.com`
- **Password:** `<CHANGE_ME>`
- **Role:** `ADMIN`

The password is now properly hashed using SHA-256 with salt, making it secure and compatible with the authentication system.
