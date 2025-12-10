package com.example.HealthScheduler.controller;

import com.example.HealthScheduler.dto.appointment.AppointmentDetailsDTO;
import com.example.HealthScheduler.dto.appointment.AppointmentRegistrationDTO;
import com.example.HealthScheduler.enums.AppointmentStatus;
import com.example.HealthScheduler.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Appointments", description = "Appointment management endpoints")
@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "Schedule a new appointment")
    @PostMapping("/doctor/{doctorId}")
    public ResponseEntity<AppointmentDetailsDTO> schedule(
            @PathVariable Long doctorId,
            @Valid @RequestBody AppointmentRegistrationDTO dto) {
        AppointmentDetailsDTO response = appointmentService.create(doctorId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get appointment by ID")
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDetailsDTO> getById(@PathVariable Long id) {
        AppointmentDetailsDTO response = appointmentService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all appointments")
    @GetMapping
    public ResponseEntity<Page<AppointmentDetailsDTO>> getAll(
            @PageableDefault(page = 0, size = 20, sort = "appointmentDate", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<AppointmentDetailsDTO> response = appointmentService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get appointments by patient")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Page<AppointmentDetailsDTO>> getByPatient(
            @PathVariable Long patientId,
            @PageableDefault(size = 20, sort = "appointmentDate", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<AppointmentDetailsDTO> response = appointmentService.findByPatient(patientId, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get appointments by doctor")
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Page<AppointmentDetailsDTO>> getByDoctor(
            @PathVariable Long doctorId,
            @PageableDefault(size = 20, sort = "appointmentDate", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<AppointmentDetailsDTO> response = appointmentService.findByDoctor(doctorId, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get appointments by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<AppointmentDetailsDTO>> getByStatus(
            @PathVariable AppointmentStatus status,
            @PageableDefault(size = 20, sort = "appointmentDate", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<AppointmentDetailsDTO> response = appointmentService.findByStatus(status, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Confirm an appointment")
    @PutMapping("/{id}/confirm")
    public ResponseEntity<AppointmentDetailsDTO> confirm(@PathVariable Long id) {
        AppointmentDetailsDTO response = appointmentService.confirm(id);
        return ResponseEntity.ok(response);
    }
}