package com.example.healthcare_customercare.service;

import com.example.healthcare_customercare.entity.Role;
import com.example.healthcare_customercare.entity.User;
import com.example.healthcare_customercare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SystemAdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findById(email);
        return user.orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Role is required");
        }

        if (userRepository.existsById(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        if (!isValidRole(user.getRole())) {
            throw new IllegalArgumentException("Invalid role: " + user.getRole());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Role is required");
        }

        Optional<User> existingUserOpt = userRepository.findById(user.getEmail());
        if (existingUserOpt.isEmpty()) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " does not exist");
        }

        if (!isValidRole(user.getRole())) {
            throw new IllegalArgumentException("Invalid role: " + user.getRole());
        }

        User existingUser = existingUserOpt.get();

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            user.setPassword(existingUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    public boolean deleteUser(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (!userRepository.existsById(email)) {
            throw new IllegalArgumentException("User with email " + email + " does not exist");
        }

        Optional<User> user = userRepository.findById(email);
        if (user.isPresent() && user.get().isAdmin()) {
            throw new IllegalArgumentException("Cannot delete admin users");
        }

        try {
            userRepository.deleteById(email);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage());
        }
    }

    public List<User> getUsersByRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role is required");
        }

        if (!isValidRole(role)) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        return userRepository.findByRole(role);
    }

    public List<User> searchUsers(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllUsers();
        }

        String searchQuery = "%" + query.toLowerCase() + "%";
        return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                searchQuery, searchQuery, searchQuery);
    }

    public Map<String, Long> getUserStatistics() {
        Map<String, Long> stats = new HashMap<>();

        long totalUsers = userRepository.count();
        stats.put("totalUsers", totalUsers);
        stats.put("activeUsers", totalUsers);

        long staffMembers = userRepository.countByRoleNot("CUSTOMER");
        stats.put("staffMembers", staffMembers);

        long customers = userRepository.countByRole("CUSTOMER");
        stats.put("customers", customers);

        return stats;
    }

    public long getUserCountByRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role is required");
        }

        return userRepository.countByRole(role);
    }

    private boolean isValidRole(String role) {
        if (role == null) {
            return false;
        }

        return roleService.roleNameExists(role);
    }

    public List<String> getAvailableRoles() {
        return roleService.getAllRoles().stream()
                .map(Role::getRoleName)
                .collect(java.util.stream.Collectors.toList());
    }

    public String getRoleDisplayName(String role) {
        if (role == null) {
            return "Unknown";
        }

        switch (role.toUpperCase()) {
            case "CUSTOMER":
                return "Customer";
            case "RECEPTIONIST":
                return "Receptionist";
            case "STAFF_COORDINATOR":
                return "Staff Coordinator";
            case "CUSTOMER_SUPPORT_MANAGER":
                return "Customer Support Manager";
            case "SENIOR_MEDICAL_OFFICER":
                return "Senior Medical Officer";
            case "ADMIN":
                return "System Admin";
            default:
                return role;
        }
    }

    public String getRoleDescription(String role) {
        if (role == null) {
            return "Unknown role";
        }

        switch (role.toUpperCase()) {
            case "CUSTOMER":
                return "Patients who can book appointments and access their medical records";
            case "RECEPTIONIST":
                return "Front desk staff who manage appointments and patient registration";
            case "STAFF_COORDINATOR":
                return "Manages doctor schedules and staff assignments";
            case "CUSTOMER_SUPPORT_MANAGER":
                return "Handles customer support tickets and feedback";
            case "SENIOR_MEDICAL_OFFICER":
                return "Senior medical staff with access to patient history and reports";
            case "ADMIN":
                return "Full system access for user management and system administration";
            default:
                return "Unknown role";
        }
    }

    public boolean canDeleteUser(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        Optional<User> user = userRepository.findById(email);
        if (user.isPresent()) {
            return !user.get().isAdmin();
        }

        return false;
    }

    public List<String> getUserPermissions(String role) {
        if (role == null) {
            return List.of();
        }

        switch (role.toUpperCase()) {
            case "CUSTOMER":
                return List.of(
                        "Book appointments",
                        "View medical reports",
                        "Submit feedback",
                        "Create support tickets"
                );
            case "RECEPTIONIST":
                return List.of(
                        "Manage appointments",
                        "Register patients",
                        "View room availability",
                        "Update patient info"
                );
            case "STAFF_COORDINATOR":
                return List.of(
                        "Manage doctor schedules",
                        "Assign staff",
                        "View system reports",
                        "Manage rooms"
                );
            case "CUSTOMER_SUPPORT_MANAGER":
                return List.of(
                        "Manage support tickets",
                        "Respond to feedback",
                        "View customer issues",
                        "Generate reports"
                );
            case "SENIOR_MEDICAL_OFFICER":
                return List.of(
                        "View patient history",
                        "Access medical reports",
                        "Manage recommendations",
                        "Review cases"
                );
            case "ADMIN":
                return List.of(
                        "Manage all users",
                        "Assign roles",
                        "System configuration",
                        "Full access"
                );
            default:
                return List.of();
        }
    }
}