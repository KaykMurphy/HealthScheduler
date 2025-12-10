package com.example.HealthScheduler.dto.schedule;

import java.time.LocalDateTime;

public record AvailableSlotDTO(
        LocalDateTime dateTime,
        boolean available
) {
}
