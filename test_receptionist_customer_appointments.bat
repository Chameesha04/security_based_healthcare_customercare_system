@echo off
echo Testing Receptionist-Created Appointments in Customer History
echo =============================================================

echo.
echo Application is running on http://localhost:8080
echo.

echo This test verifies that appointments created by receptionists
echo appear in the customer's appointment history.
echo.

echo Test Steps:
echo ===========

echo 1. LOGIN AS RECEPTIONIST:
echo    Go to: http://localhost:8080/login.html
echo    Email: receptionist@arogya.com
echo    Password: receptionist123
echo    (Or use existing receptionist credentials)
echo.

echo 2. CREATE APPOINTMENT AS RECEPTIONIST:
echo    After login, go to receptionist dashboard
echo    Click "Create New Appointment" or similar button
echo    Fill out the appointment form:
echo    - Patient Name: John Doe
echo    - Patient Email: customer@example.com (or existing customer email)
echo    - Select Doctor: Choose any available doctor
echo    - Appointment Date: Choose future date
echo    - Time Slot: Select available time slot
echo    - Room: Will be auto-selected
echo    Click "Create Appointment"
echo    Should show success message
echo.

echo 3. LOGIN AS CUSTOMER:
echo    Go to: http://localhost:8080/login.html
echo    Email: customer@example.com (same email used in step 2)
echo    Password: <CHANGE_ME>
echo    (Or use the customer credentials)
echo.

echo 4. CHECK CUSTOMER APPOINTMENT HISTORY:
echo    After customer login, go to customer dashboard:
echo    http://localhost:8080/customer.html?email=customer@example.com
echo    Click "Booked Appointments" button in sidebar
echo    The appointment created by receptionist should appear in the list
echo.

echo 5. ALTERNATIVE - CHECK FROM APPOINTMENT PAGE:
echo    Go to: http://localhost:8080/appointment.html?email=customer@example.com
echo    Click "View My Appointments" button
echo    The receptionist-created appointment should be visible
echo.

echo 6. VERIFY APPOINTMENT DETAILS:
echo    The appointment should show:
echo    - Doctor name and specialization
echo    - Appointment date and time
echo    - Patient name (should match what receptionist entered)
echo    - Room number
echo    - Appointment ID
echo    - Status (Upcoming/Past)
echo.

echo Expected Results:
echo =================
echo - Receptionist can successfully create appointments
echo - Appointments are stored with patient email
echo - Customer can see receptionist-created appointments in their history
echo - All appointment details are correctly displayed
echo - Both customer dashboard and appointment page show the same data
echo.

echo Technical Details:
echo ==================
echo - Receptionist uses: /receptionist/appointment/create endpoint
echo - Customer history uses: /api/appointments/patient endpoint
echo - Both use same AppointmentService.createAppointment() method
echo - Appointments are stored with patientEmail field
echo - Customer history queries by patientEmail using findByPatientEmail()
echo.

echo If you get errors:
echo ==================
echo - Check browser console (F12) for JavaScript errors
echo - Check network tab for failed API requests
echo - Verify receptionist and customer accounts exist
echo - Check application logs in terminal
echo - Ensure doctor schedules are available
echo.

echo Test Data Suggestions:
echo ======================
echo - Receptionist Email: receptionist@arogya.com
echo - Customer Email: customer@example.com
echo - Patient Name: John Doe, Jane Smith, etc.
echo - Use future dates for appointments
echo.

pause
