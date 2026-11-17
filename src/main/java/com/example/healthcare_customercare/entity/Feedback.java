package com.example.healthcare_customercare.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
public class Feedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "feedback_type", nullable = false)
    private String feedbackType;
    
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Column(name = "rating", nullable = false)
    private Integer rating;
    
    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "deleted_by_customer")
    private Boolean deletedByCustomer = false;

    @Column(name = "deleted_by_manager")
    private Boolean deletedByManager = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by_email")
    private String deletedByEmail;
    
    // Default constructor
    public Feedback() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor with required fields
    public Feedback(String feedbackType, String message, Integer rating, String userEmail) {
        this.feedbackType = feedbackType;
        this.message = message;
        this.rating = rating;
        this.userEmail = userEmail;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getFeedbackId() {
        return feedbackId;
    }
    
    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getFeedbackType() {
        return feedbackType;
    }
    
    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Boolean getDeletedByCustomer() {
        return deletedByCustomer;
    }

    public void setDeletedByCustomer(Boolean deletedByCustomer) {
        this.deletedByCustomer = deletedByCustomer;
    }

    public Boolean getDeletedByManager() {
        return deletedByManager;
    }

    public void setDeletedByManager(Boolean deletedByManager) {
        this.deletedByManager = deletedByManager;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedByEmail() {
        return deletedByEmail;
    }

    public void setDeletedByEmail(String deletedByEmail) {
        this.deletedByEmail = deletedByEmail;
    }
    
    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", createdAt=" + createdAt +
                ", feedbackType='" + feedbackType + '\'' +
                ", message='" + message + '\'' +
                ", rating=" + rating +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
