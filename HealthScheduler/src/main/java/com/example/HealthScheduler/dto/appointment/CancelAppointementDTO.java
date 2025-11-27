package com.example.HealthScheduler.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CancelAppointementDTO(

        @NotBlank
        @Size(min = 10, max = 500)
        String reason
) {
}
