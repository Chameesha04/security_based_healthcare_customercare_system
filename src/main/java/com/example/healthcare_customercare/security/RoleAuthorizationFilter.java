package com.example.healthcare_customercare.security;

import com.example.healthcare_customercare.entity.User;
import com.example.healthcare_customercare.service.AuditLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RoleAuthorizationFilter extends OncePerRequestFilter {

    private final AuditLogService auditLogService;

    public RoleAuthorizationFilter(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        User loggedInUser = session != null ? (User) session.getAttribute("loggedInUser") : null;

        if (loggedInUser == null) {
            response.sendRedirect("/login.html?error=login_required");
            return;
        }

        String role = loggedInUser.getRole();

        if (!hasAccess(path, role, request.getMethod())) {
            auditLogService.log(
                    "ACCESS_DENIED",
                    loggedInUser,
                    path,
                    request,
                    "User attempted to access unauthorized URL"
            );

            response.sendRedirect("/login.html?error=access_denied");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return path.equals("/")
                || path.equals("/login")
                || path.equals("/login.html")
                || path.equals("/signup")
                || path.equals("/signup.html")
                || path.equals("/logout")
                || path.equals("/error")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.startsWith("/webjars/")
                || path.equals("/favicon.ico");
    }

    private boolean hasAccess(String path, String role, String method) {
        if (role == null) {
            return false;
        }

        if (path.startsWith("/api/admin")) {
            return role.equals("ADMIN");
        }

        if (path.startsWith("/api/patient")
                || path.startsWith("/api/medical-report")
                || path.startsWith("/api/medical-reports")
                || path.startsWith("/api/medical-recommendation")
                || path.startsWith("/api/medical-recommendations")) {
            return role.equals("SENIOR_MEDICAL_OFFICER");
        }

        if (path.equals("/api/appointments/patient")
                || path.equals("/api/feedback/history")
                || path.equals("/api/support-tickets/patient")
                || path.startsWith("/api/support-tickets/customer/")
                || path.startsWith("/api/feedback/customer/")) {
            return role.equals("CUSTOMER");
        }

        if (path.startsWith("/api/appointments/by-date")) {
            return role.equals("RECEPTIONIST");
        }

        if (path.startsWith("/api/support-tickets")) {
            return role.equals("CUSTOMER_SUPPORT_MANAGER");
        }

        if (path.startsWith("/api/feedback")) {
            return role.equals("CUSTOMER_SUPPORT_MANAGER");
        }

        if (path.startsWith("/api/export/tickets") || path.startsWith("/api/export/feedback")) {
            return role.equals("CUSTOMER_SUPPORT_MANAGER");
        }

        if (path.startsWith("/api/notifications/test") || path.endsWith("/debug")) {
            return role.equals("ADMIN");
        }

        if (path.startsWith("/api/notifications")) {
            return role.equals("CUSTOMER")
                    || role.equals("RECEPTIONIST")
                    || role.equals("STAFF_COORDINATOR")
                    || role.equals("CUSTOMER_SUPPORT_MANAGER")
                    || role.equals("SENIOR_MEDICAL_OFFICER")
                    || role.equals("ADMIN");
        }

        if (path.startsWith("/api/schedules")) {
            if ("GET".equalsIgnoreCase(method)) {
                return role.equals("CUSTOMER")
                        || role.equals("RECEPTIONIST")
                        || role.equals("STAFF_COORDINATOR");
            }
            return role.equals("STAFF_COORDINATOR");
        }

        if (path.startsWith("/system-admin")) {
            return role.equals("ADMIN");
        }

        if (path.startsWith("/receptionist")) {
            return role.equals("RECEPTIONIST");
        }

        if (path.startsWith("/staff-coordinator")) {
            return role.equals("STAFF_COORDINATOR");
        }

        if (path.startsWith("/senior-medical-officer")) {
            return role.equals("SENIOR_MEDICAL_OFFICER");
        }

        if (path.equals("/customer-support-manager.html")
                || path.equals("/ticket-reply.html")) {
            return role.equals("CUSTOMER_SUPPORT_MANAGER");
        }

        if (path.equals("/customer.html")
                || path.equals("/appointment.html")
                || path.equals("/feedback.html")
                || path.equals("/support-ticket.html")
                || path.equals("/profile-settings.html")
                || path.startsWith("/customer/")) {
            return role.equals("CUSTOMER");
        }

        if (path.startsWith("/test-")) {
            return role.equals("ADMIN");
        }

        return true;
    }
}
