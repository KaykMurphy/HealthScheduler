package com.example.HealthScheduler.dto.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PatientUpdateDTO(

        @NotBlank
        @Size(min = 3, max = 100)
        String name,

        @NotBlank
        @Pattern(regexp = "\\d{10,11}")
        String phone,

        @Email
        @NotBlank
        String email,

        @Valid
        AddressDTO address
) {
}
