package com.example.HealthScheduler.dto.appointment;

// import com.example.HealthScheduler.enums.AppointmentStatus;
import com.example.HealthScheduler.enums.AppointmentStatus;
import com.example.HealthScheduler.enums.Specialization;


import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetailsDTO extends RepresentationModel<AppointmentDetailsDTO> {

    private Long id;
    private Long doctorId;
    private Long patientId;
    private String doctorName;
    private Specialization specialization;
    private LocalDateTime appointmentDate;
    private Integer durationMinutes;
    private AppointmentStatus status;
    private String cancellationReason;
    private LocalDateTime createdAt;
}