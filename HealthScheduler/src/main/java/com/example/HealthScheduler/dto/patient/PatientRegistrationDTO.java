package com.example.HealthScheduler.dto.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientRegistrationDTO(
        @NotBlank
        @Size(min = 3, max = 100)
        String name,

        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String cpf,

        @NotNull
        @Past
        LocalDate birthDate,

        @NotBlank
        @Pattern(regexp = "\\d{10,11}")
        String phone,

        @Email
        @NotBlank
        String email
/*
        @NotNull
        @Valid*/
        //AddressDTo address
) {
}
