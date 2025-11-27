package com.example.HealthScheduler.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDetailsDTO extends RepresentationModel<PatientDetailsDTO> {

    private Long id;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private Integer age;
    private String phone;
    private String email;
    private AddressDTO address;
    private LocalDateTime createdAt;


}
