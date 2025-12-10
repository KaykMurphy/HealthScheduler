package com.example.HealthScheduler.dto.schedule;

import com.example.HealthScheduler.enums.DayOfWeek;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record DoctorScheduleDTO(

        @NotNull
        DayOfWeek dayOfWeek,

        @NotNull
        LocalTime startTime,

        @NotNull
        LocalTime endTime
) {
}
