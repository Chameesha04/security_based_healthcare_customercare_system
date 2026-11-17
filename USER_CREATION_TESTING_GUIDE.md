# User Creation Testing Guide

## Issue Fixed
The SystemAdminService was not hashing passwords when creating users, which would cause authentication issues.

## Changes Made

### 1. Fixed Password Hashing in SystemAdminService
- Added `UserService` dependency to `SystemAdminService`
- Updated `createUser()` method to hash passwords before saving
- Updated `updateUser()` method to hash passwords when updating

### 2. Password Security
- All new users now have properly hashed passwords using SHA-256 with salt
- Passwords are hashed before being stored in the database
- Authentication system can properly verify hashed passwords

## Testing User Creation

### Step 1: Start the Application
```bash
mvn spring-boot:run
```

### Step 2: Login as Admin
1. Go to: `http://localhost:8080/login.html`
2. Use admin credentials:
   - **Email:** `admin@arogya.com`
   - **Password:** `<CHANGE_ME>`

### Step 3: Create a New User
1. You should be redirected to the System Admin dashboard
2. Scroll down to the "Add New User" section
3. Fill out the form with test data:
   - **First Name:** `John`
   - **Last Name:** `Doe`
   - **Email:** `john.doe@test.com`
   - **Phone Number:** `555-1234`
   - **Password:** `testpassword123`
   - **Role:** `CUSTOMER`
4. Click "Create User"

### Step 4: Verify User Creation
1. The user should appear in the "User Management" table below
2. You should see a success notification
3. The user count statistics should update

### Step 5: Test Database Update
Run the SQL script `test_user_creation.sql` to verify:
- User exists in database
- Password is properly hashed (not plain text)
- All fields are correctly stored

### Step 6: Test User Login
1. Go to: `http://localhost:8080/login.html`
2. Try logging in with the new user:
   - **Email:** `john.doe@test.com`
   - **Password:** `testpassword123`
3. Should redirect to customer dashboard

## Expected Results

### Database Verification
When you run the SQL script, you should see:
- User appears in the users table
- Password format shows "HASHED" (not "PLAIN_TEXT")
- Password length is > 20 characters
- All user fields are properly populated

### Frontend Verification
- User appears in the admin dashboard user list
- User statistics update correctly
- Success notification appears
- No error messages

### Authentication Verification
- New user can login successfully
- User is redirected to appropriate dashboard based on role
- Password verification works correctly

## Troubleshooting

### If User Creation Fails:

1. **Check Console Logs**: Look for error messages in the application console
2. **Check Network Tab**: Open browser developer tools and check for API errors
3. **Verify Database Connection**: Ensure database is running and accessible
4. **Check Email Uniqueness**: Make sure the email doesn't already exist

### If User Can't Login:

1. **Check Password Hashing**: Run the SQL script to verify password is hashed
2. **Check Role Assignment**: Verify the role is correctly set
3. **Check Email Format**: Ensure email is valid format

### Common Issues:

1. **Duplicate Email**: Each email must be unique
2. **Invalid Role**: Must be one of: CUSTOMER, RECEPTIONIST, STAFF_COORDINATOR, CUSTOMER_SUPPORT_MANAGER, SENIOR_MEDICAL_OFFICER, ADMIN
3. **Empty Fields**: All required fields must be filled
4. **Password Length**: Password should be at least 6 characters

## Test Different User Types

Try creating users with different roles:
- **Customer**: Should redirect to customer dashboard
- **Receptionist**: Should redirect to receptionist dashboard  
- **Staff Coordinator**: Should redirect to staff coordinator dashboard
- **Customer Support Manager**: Should redirect to support manager dashboard
- **Senior Medical Officer**: Should redirect to medical officer dashboard

## Database Queries for Verification

```sql
-- Check all users
SELECT email, first_name, last_name, role, 
       CASE WHEN LENGTH(password) > 20 THEN 'HASHED' ELSE 'PLAIN_TEXT' END as password_format
FROM users ORDER BY role;

-- Check specific user
SELECT * FROM users WHERE email = 'john.doe@test.com';

-- Count users by role
SELECT role, COUNT(*) FROM users GROUP BY role;
```

The user creation functionality should now work properly with secure password hashing and proper database updates!
