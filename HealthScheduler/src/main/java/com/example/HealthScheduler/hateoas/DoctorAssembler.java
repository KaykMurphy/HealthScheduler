package com.example.HealthScheduler.hateoas;

import com.example.HealthScheduler.controller.DoctorController;
import com.example.HealthScheduler.dto.doctor.DoctorDetailsDTO;
import com.example.HealthScheduler.entity.Doctor;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DoctorAssembler extends RepresentationModelAssemblerSupport<Doctor, DoctorDetailsDTO> {

    public DoctorAssembler() {
        super(DoctorController.class, DoctorDetailsDTO.class);
    }

    @Override
    public DoctorDetailsDTO toModel(Doctor entity) {

        DoctorDetailsDTO dto = new DoctorDetailsDTO(
                entity.getId(),
                entity.getName(),
                entity.getCrm(),
                entity.getSpecialization(),
                entity.getPhone(),
                entity.getEmail()
        );

        dto.add(linkTo(methodOn(DoctorController.class)
                .getById(dto.getId())).withSelfRel());

        dto.add(linkTo(methodOn(DoctorController.class)
                .getAll(null)).withRel("doctors"));

        dto.add(linkTo(methodOn(DoctorController.class)
                .update(entity.getId(), null))
                .withRel("update")
                .withType("PUT"));

        dto.add(linkTo(methodOn(DoctorController.class)
                .deactivate(entity.getId()))
                .withRel("deactivate"));

        dto.add(linkTo(methodOn(DoctorController.class)
                .activate(entity.getId()))
                .withRel("activate"));

        dto.add(linkTo(methodOn(DoctorController.class)
                .getBySpecialization(entity.getSpecialization(), null))
                .withRel("by-specialization"));

        return dto;
    }
}