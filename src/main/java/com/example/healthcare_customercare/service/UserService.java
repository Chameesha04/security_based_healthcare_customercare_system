package com.example.healthcare_customercare.service;

import com.example.healthcare_customercare.entity.User;
import com.example.healthcare_customercare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean verifyPassword(String password, String storedPassword) {
        if (password == null || storedPassword == null) {
            return false;
        }

        return passwordEncoder.matches(password, storedPassword);
    }

    public Optional<User> authenticateUser(String email, String password) {
        System.out.println("=== USER SERVICE AUTH DEBUG ===");
        System.out.println("Looking for user with email: " + email);

        Optional<User> user = userRepository.findByEmail(email);
        System.out.println("User found in DB: " + user.isPresent());

        if (user.isPresent()) {
            User foundUser = user.get();
            System.out.println("Found user role: " + foundUser.getRole());

            boolean passwordMatch = verifyPassword(password, foundUser.getPassword());
            System.out.println("Password verification result: " + passwordMatch);

            if (passwordMatch) {
                return user;
            }
        }

        return Optional.empty();
    }

    public Optional<User> authenticateCustomer(String email, String password) {
        Optional<User> customer = userRepository.findByEmailAndRole(email, "CUSTOMER");

        if (customer.isPresent() && verifyPassword(password, customer.get().getPassword())) {
            return customer;
        }

        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findCustomerByEmail(String email) {
        return userRepository.findByEmailAndRole(email, "CUSTOMER");
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean customerEmailExists(String email) {
        return userRepository.existsByEmailAndRole(email, "CUSTOMER");
    }

    public List<User> getAllCustomers() {
        return userRepository.findByRole("CUSTOMER");
    }

    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }

    public User createCustomer(String firstName, String lastName, String email, String phoneNumber, String password) {
        User customer = new User(firstName, lastName, email, phoneNumber, hashPassword(password), "CUSTOMER");
        return userRepository.save(customer);
    }

    public User createStaffCoordinator(String firstName, String lastName, String email, String phoneNumber, String password) {
        User staffCoordinator = new User(firstName, lastName, email, phoneNumber, hashPassword(password), "STAFF_COORDINATOR");
        return userRepository.save(staffCoordinator);
    }

    public User createReceptionist(String firstName, String lastName, String email, String phoneNumber, String password) {
        User receptionist = new User(firstName, lastName, email, phoneNumber, hashPassword(password), "RECEPTIONIST");
        return userRepository.save(receptionist);
    }

    public User createSeniorMedicalOfficer(String firstName, String lastName, String email, String phoneNumber, String password) {
        User smo = new User(firstName, lastName, email, phoneNumber, hashPassword(password), "SENIOR_MEDICAL_OFFICER");
        return userRepository.save(smo);
    }

    public User createCustomerSupportManager(String firstName, String lastName, String email, String phoneNumber, String password) {
        User customerSupportManager = new User(firstName, lastName, email, phoneNumber, hashPassword(password), "CUSTOMER_SUPPORT_MANAGER");
        return userRepository.save(customerSupportManager);
    }

    public User updateUserProfile(String email, String firstName, String lastName, String phoneNumber) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        User user = userOpt.get();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);

        return userRepository.save(user);
    }

    public User updateUserPassword(String email, String currentPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        User user = userOpt.get();

        if (!verifyPassword(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setPassword(hashPassword(newPassword));

        return userRepository.save(user);
    }
}