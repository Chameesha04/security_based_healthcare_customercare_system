package com.example.healthcare_customercare.controller;

import com.example.healthcare_customercare.entity.Notification;
import com.example.healthcare_customercare.entity.User;
import com.example.healthcare_customercare.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for notification operations
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * Get all notifications for the logged-in user.
     */
    @GetMapping("/user/{userEmail}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable String userEmail, HttpSession session) {
        String sessionEmail = getAuthorizedEmail(userEmail, session);
        if (sessionEmail == null) {
            return ResponseEntity.status(403).build();
        }

        try {
            return ResponseEntity.ok(notificationService.getNotificationsByUser(sessionEmail));
        } catch (Exception e) {
            System.err.println("Error getting notifications: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Get unread notifications for the logged-in user.
     */
    @GetMapping("/user/{userEmail}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotificationsByUser(@PathVariable String userEmail, HttpSession session) {
        String sessionEmail = getAuthorizedEmail(userEmail, session);
        if (sessionEmail == null) {
            return ResponseEntity.status(403).build();
        }

        try {
            return ResponseEntity.ok(notificationService.getUnreadNotificationsByUser(sessionEmail));
        } catch (Exception e) {
            System.err.println("Error getting unread notifications: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Get unread notification count for the logged-in user.
     */
    @GetMapping("/user/{userEmail}/count")
    public ResponseEntity<Long> getUnreadNotificationCount(@PathVariable String userEmail, HttpSession session) {
        String sessionEmail = getAuthorizedEmail(userEmail, session);
        if (sessionEmail == null) {
            return ResponseEntity.status(403).build();
        }

        try {
            return ResponseEntity.ok(notificationService.getUnreadNotificationCount(sessionEmail));
        } catch (Exception e) {
            System.err.println("Error getting notification count: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Mark a notification as read if it belongs to the logged-in user.
     */
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long notificationId, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) {
            return ResponseEntity.status(403).build();
        }

        Optional<Notification> existing = notificationService.getNotificationById(notificationId);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!existing.get().getUserEmail().equalsIgnoreCase(user.getEmail())) {
            return ResponseEntity.status(403).build();
        }

        try {
            return ResponseEntity.ok(notificationService.markAsRead(notificationId));
        } catch (Exception e) {
            System.err.println("Error marking notification as read: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Mark all notifications as read for the logged-in user.
     */
    @PutMapping("/user/{userEmail}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable String userEmail, HttpSession session) {
        String sessionEmail = getAuthorizedEmail(userEmail, session);
        if (sessionEmail == null) {
            return ResponseEntity.status(403).build();
        }

        try {
            notificationService.markAllAsRead(sessionEmail);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Error marking all notifications as read: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Delete a notification if it belongs to the logged-in user.
     */
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) {
            return ResponseEntity.status(403).build();
        }

        Optional<Notification> existing = notificationService.getNotificationById(notificationId);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!existing.get().getUserEmail().equalsIgnoreCase(user.getEmail())) {
            return ResponseEntity.status(403).build();
        }

        try {
            notificationService.deleteNotification(notificationId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Error deleting notification: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Debug endpoint for admins only. The authorization filter blocks non-admin users.
     */
    @GetMapping("/user/{userEmail}/debug")
    public ResponseEntity<List<Notification>> getAllNotificationsByUser(@PathVariable String userEmail) {
        try {
            return ResponseEntity.ok(notificationService.getAllNotificationsByUser(userEmail));
        } catch (Exception e) {
            System.err.println("Error getting all notifications: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Create a test notification. The authorization filter blocks non-admin users.
     */
    @PostMapping("/test")
    public ResponseEntity<Notification> createTestNotification(
            @RequestParam String userEmail,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam(defaultValue = "SYSTEM") String type,
            @RequestParam(defaultValue = "MEDIUM") String priority) {
        try {
            Notification notification = notificationService.createNotification(userEmail, title, message, type, priority);
            return ResponseEntity.ok(notification);
        } catch (Exception e) {
            System.err.println("Error creating test notification: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    private String getAuthorizedEmail(String requestedEmail, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null || user.getEmail() == null || requestedEmail == null) {
            return null;
        }

        if (!user.getEmail().equalsIgnoreCase(requestedEmail)) {
            return null;
        }

        return user.getEmail();
    }

    private User getLoggedInUser(HttpSession session) {
        return session != null ? (User) session.getAttribute("loggedInUser") : null;
    }
}
