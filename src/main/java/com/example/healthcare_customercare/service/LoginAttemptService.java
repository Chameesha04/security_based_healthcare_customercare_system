package com.example.healthcare_customercare.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class LoginAttemptService {

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final Duration LOCKOUT_DURATION = Duration.ofMinutes(15);

    private final ConcurrentMap<String, LoginAttempt> attempts = new ConcurrentHashMap<>();

    public boolean isLocked(String email) {
        LoginAttempt attempt = attempts.get(normalize(email));
        if (attempt == null || attempt.lockedUntil == null) {
            return false;
        }

        if (Instant.now().isAfter(attempt.lockedUntil)) {
            attempts.remove(normalize(email));
            return false;
        }

        return true;
    }

    public long getRemainingLockoutMinutes(String email) {
        LoginAttempt attempt = attempts.get(normalize(email));
        if (attempt == null || attempt.lockedUntil == null) {
            return 0;
        }

        long seconds = Duration.between(Instant.now(), attempt.lockedUntil).toSeconds();
        if (seconds <= 0) {
            attempts.remove(normalize(email));
            return 0;
        }

        return Math.max(1, (long) Math.ceil(seconds / 60.0));
    }

    public int recordFailedAttempt(String email) {
        String key = normalize(email);
        LoginAttempt attempt = attempts.compute(key, (ignored, existing) -> {
            LoginAttempt current = existing != null ? existing : new LoginAttempt();
            current.failedAttempts++;
            if (current.failedAttempts >= MAX_FAILED_ATTEMPTS) {
                current.lockedUntil = Instant.now().plus(LOCKOUT_DURATION);
            }
            return current;
        });

        return Math.max(0, MAX_FAILED_ATTEMPTS - attempt.failedAttempts);
    }

    public void recordSuccessfulLogin(String email) {
        attempts.remove(normalize(email));
    }

    private String normalize(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }

    private static class LoginAttempt {
        private int failedAttempts;
        private Instant lockedUntil;
    }
}
