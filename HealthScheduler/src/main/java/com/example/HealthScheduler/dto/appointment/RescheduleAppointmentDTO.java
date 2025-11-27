package com.example.HealthScheduler.dto.appointment;

import com.example.HealthScheduler.entity.Appointment;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RescheduleAppointmentDTO(

        @NotNull
        @Future
        LocalDateTime newAppointmentDate
) {
}
