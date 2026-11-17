# Security Features

This document summarizes the security practices implemented in the healthcare customer care system.

## Authentication

- User passwords are stored with BCrypt hashing instead of plain text.
- Database credentials are read from environment variables:
  - `DB_USERNAME`
  - `DB_PASSWORD`
- Login failure protection is enabled:
  - 5 failed login attempts lock the email for 15 minutes.
  - The lockout is in-memory for development, so it resets when the app restarts.

## Authorization

- Role-based access control is enforced with `RoleAuthorizationFilter`.
- Protected pages and APIs require an active session.
- Users are redirected to login when they are not authenticated.
- Wrong-role access attempts are blocked and logged as `ACCESS_DENIED`.

Main roles:

- `ADMIN`
- `CUSTOMER`
- `RECEPTIONIST`
- `STAFF_COORDINATOR`
- `CUSTOMER_SUPPORT_MANAGER`
- `SENIOR_MEDICAL_OFFICER`

## Session And Logout

- Login stores the authenticated user in the HTTP session.
- Logout records a `LOGOUT` audit event, invalidates the session, clears the session cookie, and redirects to login.
- The login controller prevents switching to another account while already logged in.

## CSRF Protection

- CSRF protection is enabled through Spring Security.
- HTML forms include CSRF hidden fields.
- JavaScript `fetch()` calls include the CSRF header for POST, PUT, and DELETE requests.

## Input Validation

Server-side validation was added for risky input areas:

- Signup
- Admin user creation and editing
- Support ticket creation
- Feedback submission
- Appointment booking

Validation blocks empty fields, invalid emails, invalid ratings, invalid appointment data, and attempts to submit data under another user's email.

## Stored XSS Protection

Free-text values are escaped before being shown in the UI, especially:

- Support ticket subject and description
- Ticket replies
- Feedback messages
- Medical report details
- Medical recommendations
- Notifications
- Appointment and schedule display text

Test payload:

```html
<img src=x onerror=alert('xss')>
```

Expected safe result:

- The text is displayed as plain text, or blocked by validation.
- No browser alert popup should appear.

## Audit Logging

Important security and business actions are recorded in `audit_logs`.

Examples:

- `LOGIN_SUCCESS`
- `LOGIN_FAILED`
- `LOGIN_LOCKED`
- `LOGOUT`
- `ACCESS_DENIED`
- `MEDICAL_REPORT_CREATED`
- `MEDICAL_RECOMMENDATION_CREATED`
- `APPOINTMENT_BOOKED`
- `APPOINTMENT_UPDATED`
- `APPOINTMENT_DELETED`
- `TICKET_REPLY_CREATED`
- `TICKET_REPLY_UPDATED`
- `TICKET_REPLY_DELETED`
- `TICKET_DELETED_BY_CUSTOMER`
- `TICKET_DELETED_BY_MANAGER`
- `TICKET_RESTORED_BY_MANAGER`
- `FEEDBACK_DELETED_BY_CUSTOMER`
- `FEEDBACK_DELETED_BY_MANAGER`
- `FEEDBACK_RESTORED_BY_MANAGER`

SQL proof:

```sql
SELECT *
FROM audit_logs
ORDER BY created_at DESC;
```

Login lockout proof:

```sql
SELECT *
FROM audit_logs
WHERE action LIKE 'LOGIN%'
ORDER BY created_at DESC;
```

Access denied proof:

```sql
SELECT *
FROM audit_logs
WHERE action = 'ACCESS_DENIED'
ORDER BY created_at DESC;
```

## Soft Delete And Restore

Tickets and feedback use soft delete instead of permanent delete.

Soft delete fields:

- `deleted_by_customer`
- `deleted_by_manager`
- `deleted_at`
- `deleted_by_email`

This allows customers and managers to hide old items while preserving accountability and audit evidence.

Ticket proof:

```sql
SELECT ticket_id, subject, user_email, deleted_by_customer, deleted_by_manager, deleted_at, deleted_by_email
FROM tickets
ORDER BY created_at DESC;
```

Feedback proof:

```sql
SELECT feedback_id, feedback_type, message, rating, user_email, deleted_by_customer, deleted_by_manager, deleted_at, deleted_by_email
FROM feedback
ORDER BY created_at DESC;
```

Delete audit proof:

```sql
SELECT *
FROM audit_logs
WHERE action LIKE '%DELETED%'
ORDER BY created_at DESC;
```

Restore audit proof:

```sql
SELECT *
FROM audit_logs
WHERE action LIKE '%RESTORED%'
ORDER BY created_at DESC;
```

## Screenshot Evidence Checklist

For the final report, capture screenshots of:

- `audit_logs` showing login success, login failure, login lockout, and logout.
- `audit_logs` showing ticket and feedback soft delete.
- `audit_logs` showing ticket and feedback restore.
- Ticket or feedback page showing the XSS payload displayed as plain text with no popup.
- A wrong-role page access redirecting to `access_denied`.

## Remaining Production Improvements

- Store login lockouts in the database or Redis so they survive app restart.
- Add account unlock controls for admins.
- Add HTTPS for deployment.
- Add stronger Content Security Policy headers.
- Replace older demo/mock page fallbacks with strict session-only page rendering.
- Add automated security tests for authorization, CSRF, XSS, and ownership checks.
