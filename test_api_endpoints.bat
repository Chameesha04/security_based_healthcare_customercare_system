@echo off
echo Testing User Creation API Endpoints
echo ===================================

echo.
echo Application is running on http://localhost:8080
echo.

echo Testing API endpoints:
echo.

echo 1. Test user statistics endpoint:
echo    GET http://localhost:8080/system-admin/api/admin/user-statistics
echo.

echo 2. Test get all users endpoint:
echo    GET http://localhost:8080/system-admin/api/admin/users
echo.

echo 3. Test create user endpoint:
echo    POST http://localhost:8080/system-admin/api/admin/users
echo    Content-Type: application/json
echo    {
echo      "firstName": "Test",
echo      "lastName": "User", 
echo      "email": "test.user@example.com",
echo      "phoneNumber": "555-1234",
echo      "password": "<CHANGE_ME>",
echo      "role": "CUSTOMER"
echo    }
echo.

echo 4. Test admin login:
echo    Go to: http://localhost:8080/login.html
echo    Email: admin@arogya.com
echo    Password: <CHANGE_ME>
echo.

echo 5. Test user creation in UI:
echo    After login, go to admin dashboard
echo    Fill the "Add New User" form
echo    Click "Create User"
echo.

echo If everything works:
echo - API endpoints return JSON responses
echo - User creation succeeds
echo - New user appears in user list
echo - User can login with new credentials
echo.

echo If you get errors:
echo - Check browser console (F12)
echo - Check network tab for failed requests
echo - Check application logs in terminal
echo.

pause
