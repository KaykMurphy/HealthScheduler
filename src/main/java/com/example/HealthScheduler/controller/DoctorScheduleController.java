package com.example.HealthScheduler.controller;

import com.example.HealthScheduler.dto.schedule.DoctorScheduleDTO;
import com.example.HealthScheduler.service.DoctorScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Doctor Schedules", description = "Doctor schedule management endpoints")
@RestController
@RequestMapping("/api/v1/doctors/{doctorId}/schedules")
@RequiredArgsConstructor
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    @Operation(summary = "Set weekly schedule for a doctor")
    @PutMapping("/weekly")
    public ResponseEntity<List<DoctorScheduleDTO>> setWeeklySchedule(
            @PathVariable Long doctorId,
            @Valid @RequestBody List<DoctorScheduleDTO> dtos) {
        List<DoctorScheduleDTO> response = doctorScheduleService.setWeeklySchedule(doctorId, dtos);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get weekly schedule for a doctor")
    @GetMapping("/weekly")
    public ResponseEntity<List<DoctorScheduleDTO>> getWeeklySchedule(
            @PathVariable Long doctorId) {
        List<DoctorScheduleDTO> response = doctorScheduleService.getWeeklySchedule(doctorId);
        return ResponseEntity.ok(response);
    }
}