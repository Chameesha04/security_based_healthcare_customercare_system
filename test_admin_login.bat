@echo off
echo Testing Admin Login Functionality
echo ================================

echo.
echo 1. Starting application...
echo Please wait for the application to start completely.
echo.

echo 2. Once the application is running, open your browser and go to:
echo    http://localhost:8080/login.html
echo.

echo 3. Use these credentials to login as admin:
echo    Email: admin@arogya.com
echo    Password: <CHANGE_ME>
echo.

echo 4. After successful login, you should be redirected to:
echo    http://localhost:8080/system-admin/dashboard
echo.

echo 5. Optional: Test if admin user exists by visiting:
echo    http://localhost:8080/system-admin/test-admin-login?email=admin@arogya.com^&password=<CHANGE_ME>
echo.

echo 6. Optional: View all users (including admin) at:
echo    http://localhost:8080/system-admin/api/admin/users
echo.

echo If login fails, check the console logs for debug information.
echo The admin user should be created automatically when the application starts.
echo.

pause
