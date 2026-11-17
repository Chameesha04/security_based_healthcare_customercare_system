@echo off
echo Testing Appointment History Functionality
echo =========================================

echo.
echo Application is running on http://localhost:8080
echo.

echo Testing Appointment History Features:
echo.

echo 1. Test Customer Dashboard Appointment History:
echo    Go to: http://localhost:8080/customer.html?email=customer@example.com
echo    Click "Booked Appointments" button in the sidebar
echo    Should display appointment history with doctor details
echo.

echo 2. Test Appointment Page History:
echo    Go to: http://localhost:8080/appointment.html?email=customer@example.com
echo    Click "View My Appointments" button
echo    Should display appointment history in a modal/section
echo.

echo 3. Test API Endpoint Directly:
echo    GET http://localhost:8080/api/appointments/patient?email=customer@example.com
echo    Should return JSON with appointments array
echo.

echo 4. Test Appointment Booking:
echo    Go to: http://localhost:8080/appointment.html?email=customer@example.com
echo    Fill out appointment form:
echo    - Select a doctor
echo    - Choose a date
echo    - Select a time slot
echo    - Click "Book Appointment"
echo    Should redirect with success message
echo.

echo 5. Verify Appointment Appears in History:
echo    After booking, check appointment history again
echo    New appointment should appear in the list
echo.

echo Expected Results:
echo - Appointment history loads without errors
echo - Shows doctor name, specialization, department
echo - Shows appointment date, patient name, room
echo - Shows appointment ID
echo - Distinguishes between upcoming and past appointments
echo - Handles empty state when no appointments exist
echo.

echo If you get errors:
echo - Check browser console (F12) for JavaScript errors
echo - Check network tab for failed API requests
echo - Check application logs in terminal
echo - Verify customer email exists in database
echo.

echo Test Data:
echo - Use existing customer email: customer@example.com
echo - Or create new customer via signup
echo - Book appointments to test history functionality
echo.

pause
