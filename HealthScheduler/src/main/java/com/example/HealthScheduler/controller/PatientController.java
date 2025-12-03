package com.example.HealthScheduler.controller;

import com.example.HealthScheduler.dto.patient.PatientDetailsDTO;
import com.example.HealthScheduler.dto.patient.PatientRegistrationDTO;
import com.example.HealthScheduler.dto.patient.PatientUpdateDTO;
import com.example.HealthScheduler.service.PatientService;
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

@Tag(name = "Patients", description = "Patient management endpoints")
@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Register a new patient")
    @PostMapping
    public ResponseEntity<PatientDetailsDTO> register(
            @Valid @RequestBody PatientRegistrationDTO dto) {
        PatientDetailsDTO response = patientService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get patient by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailsDTO> getById(@PathVariable Long id) {
        PatientDetailsDTO response = patientService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all patients")
    @GetMapping
    public ResponseEntity<Page<PatientDetailsDTO>> getAll(
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {
        Page<PatientDetailsDTO> response = patientService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update patient information")
    @PutMapping("/{id}")
    public ResponseEntity<PatientDetailsDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PatientUpdateDTO dto) {
        PatientDetailsDTO response = patientService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a patient")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}