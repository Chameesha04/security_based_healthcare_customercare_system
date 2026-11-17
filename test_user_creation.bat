@echo off
echo Testing User Creation Functionality
echo ===================================

echo.
echo Application is running on http://localhost:8080
echo.

echo 1. First, login as admin:
echo    Go to: http://localhost:8080/login.html
echo    Email: admin@arogya.com
echo    Password: <CHANGE_ME>
echo.

echo 2. After login, you should be redirected to:
echo    http://localhost:8080/system-admin/dashboard
echo.

echo 3. Test user creation by filling the form:
echo    - First Name: Test
echo    - Last Name: User
echo    - Email: test.user@example.com
echo    - Phone: 555-9999
echo    - Password: <CHANGE_ME>
echo    - Role: CUSTOMER
echo.

echo 4. Click "Create User" and verify:
echo    - Success notification appears
echo    - User appears in the user list table
echo    - User count statistics update
echo.

echo 5. Test the new user can login:
echo    - Go back to login page
echo    - Use: test.user@example.com / <CHANGE_ME>
echo    - Should redirect to customer dashboard
echo.

echo 6. Optional: Check database directly:
echo    Run the SQL script: test_user_creation.sql
echo    Look for the new user with HASHED password format
echo.

echo 7. Optional: Test API endpoint directly:
echo    POST http://localhost:8080/system-admin/api/admin/users
echo    Content-Type: application/json
echo    {
echo      "firstName": "API",
echo      "lastName": "Test",
echo      "email": "api.test@example.com",
echo      "phoneNumber": "555-8888",
echo      "password": "<CHANGE_ME>",
echo      "role": "RECEPTIONIST"
echo    }
echo.

echo If everything works correctly:
echo - Users are created successfully
echo - Passwords are properly hashed
echo - Database is updated correctly
echo - New users can login
echo.

pause
