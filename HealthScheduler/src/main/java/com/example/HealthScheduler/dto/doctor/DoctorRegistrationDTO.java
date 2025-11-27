package com.example.HealthScheduler.dto.doctor;

import com.example.HealthScheduler.enums.Specialization;
import jakarta.validation.constraints.*;

public record DoctorRegistrationDTO(

        @NotBlank
        @Size(min = 3,max = 100)
        String name,

        @NotBlank

        //precisa ter entre 4 e 20 dígitos numéricos
        @Pattern(regexp = "\\d{4,20}")
        String crm,

        @NotNull
        Specialization specialization,

        @NotBlank
        // 10 a 11 digitos númericos
        @Pattern(regexp = "\\d{10,11}")
        String phone,

        @NotBlank
        @Email
        String email
) {
}
