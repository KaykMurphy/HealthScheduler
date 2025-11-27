package com.example.HealthScheduler.dto.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressDTO(

        @NotBlank
        String street,

        @NotBlank
        String number,

        String complement,

        @NotBlank
        String neighborhood,

        @NotBlank
        String city,

        @NotBlank
        @Size(min = 2, max = 2)
        String state,

        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String zipCode
) {
}
