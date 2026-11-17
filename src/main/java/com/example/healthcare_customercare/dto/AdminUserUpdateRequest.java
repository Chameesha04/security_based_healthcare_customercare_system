package com.example.healthcare_customercare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AdminUserUpdateRequest {

    @Size(max = 50, message = "First name must be 50 characters or fewer")
    @Pattern(regexp = "^$|^[A-Za-z .'-]+$", message = "First name contains invalid characters")
    private String firstName;

    @Size(max = 50, message = "Last name must be 50 characters or fewer")
    @Pattern(regexp = "^$|^[A-Za-z .'-]+$", message = "Last name contains invalid characters")
    private String lastName;

    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must be 100 characters or fewer")
    private String email;

    @Size(max = 25, message = "Phone number must be 25 characters or fewer")
    @Pattern(regexp = "^$|^[+0-9() .-]+$", message = "Phone number contains invalid characters")
    private String phoneNumber;

    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    @Pattern(
            regexp = "^$|^(ADMIN|CUSTOMER|RECEPTIONIST|STAFF_COORDINATOR|CUSTOMER_SUPPORT_MANAGER|SENIOR_MEDICAL_OFFICER)$",
            message = "Role is invalid"
    )
    private String role;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}