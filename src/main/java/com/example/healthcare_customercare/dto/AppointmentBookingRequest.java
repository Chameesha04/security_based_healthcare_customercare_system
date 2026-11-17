package com.example.healthcare_customercare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class AppointmentBookingRequest {

    @NotBlank(message = "Patient name is required")
    @Size(max = 100, message = "Patient name must be 100 characters or fewer")
    private String patientName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    @Size(max = 100, message = "Email must be 100 characters or fewer")
    private String patientEmail;

    @NotNull(message = "Please select a doctor")
    @Positive(message = "Please select a valid doctor")
    private Long doctorId;

    @NotNull(message = "Please select an appointment date")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate appointmentDate;

    @NotBlank(message = "Please select a time slot")
    @Size(max = 50, message = "Time slot must be 50 characters or fewer")
    private String timeSlot;

    @NotNull(message = "Room information is required")
    @Positive(message = "Room information is invalid")
    private Long roomId;

    @NotNull(message = "Schedule information is required")
    @Positive(message = "Schedule information is invalid")
    private Long scheduleId;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
