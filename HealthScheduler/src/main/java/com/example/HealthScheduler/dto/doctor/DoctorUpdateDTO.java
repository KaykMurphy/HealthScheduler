package com.example.HealthScheduler.dto.doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record DoctorUpdateDTO(

        @Size(min = 3, max = 100)
        String name,

        @Pattern(regexp = "\\d{10,11}")
        String phone,

        @Email
        String email
) {
}
