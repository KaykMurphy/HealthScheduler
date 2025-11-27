package com.example.HealthScheduler.dto.doctor;

import com.example.HealthScheduler.enums.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDetailsDTO extends RepresentationModel<DoctorDetailsDTO> {

    private Long id;
    private String name;
    private String crm;
    private Specialization specialization;
    private String phone;
    private String email;
    private boolean active;
    private LocalDateTime createdAt;


}
