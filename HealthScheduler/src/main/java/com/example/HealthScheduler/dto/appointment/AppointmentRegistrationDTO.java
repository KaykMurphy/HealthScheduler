package com.example.HealthScheduler.dto.appointment;

import com.example.HealthScheduler.entity.Appointment;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AppointmentRegistrationDTO(

        @NotNull
        Long doctorId,

        @NotNull
        Long patientId,

        @Future
        @NotNull
        LocalDateTime appointmentDate,

        @NotNull
        @Min(15)
        @Max(120)
        Integer durationMinutes
) {

    public AppointmentRegistrationDTO {

        //day util
        DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
        boolean isWeekDay = dayOfWeek.getValue() >= 1 && dayOfWeek.getValue() <= 5;
        if (!isWeekDay){
            throw new IllegalArgumentException("A Consulta deve ser marcada em um dia útil (segunda a sexta)");
        }


        //hour
        LocalTime time = appointmentDate.toLocalTime();
        LocalTime start = LocalTime.of(8,0);
        LocalTime end = LocalTime.of(18,0);

        boolean  isBussinessHour = !time.isBefore(start) && !time.isAfter(end);

        if (!isBussinessHour){
            throw new IllegalArgumentException("A consulta deve ocorrer entre 08:00 e 18:00");
        }

        if (durationMinutes % 15 != 0){
            throw new IllegalArgumentException("A duração deve ser múltiplo de 15");
        }




    }
}
