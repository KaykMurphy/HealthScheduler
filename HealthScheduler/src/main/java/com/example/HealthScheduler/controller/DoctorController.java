package com.example.HealthScheduler.controller;

import com.example.HealthScheduler.dto.doctor.DoctorDetailsDTO;
import com.example.HealthScheduler.dto.doctor.DoctorRegistrationDTO;
import com.example.HealthScheduler.dto.doctor.DoctorUpdateDTO;
import com.example.HealthScheduler.enums.Specialization;
import com.example.HealthScheduler.service.DoctorService;
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

@Tag(name = "Doctors", description = "Doctor management endpoints")
@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @Operation(summary = "Register a new doctor")
    @PostMapping
    public ResponseEntity<DoctorDetailsDTO> register(
            @Valid @RequestBody DoctorRegistrationDTO dto) {
        DoctorDetailsDTO response = doctorService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get doctor by ID")
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailsDTO> getById(@PathVariable Long id) {
        DoctorDetailsDTO response = doctorService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all active doctors")
    @GetMapping
    public ResponseEntity<Page<DoctorDetailsDTO>> getAll(
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {
        Page<DoctorDetailsDTO> response = doctorService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get doctors by specialization")
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<Page<DoctorDetailsDTO>> getBySpecialization(
            @PathVariable Specialization specialization,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<DoctorDetailsDTO> response = doctorService.findBySpecialization(specialization, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update doctor information")
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDetailsDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody DoctorUpdateDTO dto) {
        DoctorDetailsDTO response = doctorService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deactivate a doctor")
    @DeleteMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        doctorService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Activate a doctor")
    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        doctorService.activate(id);
        return ResponseEntity.ok().build();
    }


    // TODO implementar reactivate doctor
}