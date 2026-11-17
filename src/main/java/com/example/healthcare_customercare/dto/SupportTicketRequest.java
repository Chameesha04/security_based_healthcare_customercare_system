package com.example.healthcare_customercare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SupportTicketRequest {

    @NotBlank(message = "Category is required")
    @Pattern(regexp = "^(technical|appointment|billing|account|general|complaint)$", message = "Invalid support category")
    private String category;

    @NotBlank(message = "Priority is required")
    @Pattern(regexp = "^(low|medium|high|urgent)$", message = "Invalid priority")
    private String priority;

    @NotBlank(message = "Subject is required")
    @Size(max = 120, message = "Subject must be 120 characters or fewer")
    private String subject;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must be 2000 characters or fewer")
    private String description;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    @Size(max = 100, message = "Email must be 100 characters or fewer")
    private String userEmail;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
