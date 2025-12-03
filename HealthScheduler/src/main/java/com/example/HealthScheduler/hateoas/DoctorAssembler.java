package com.example.HealthScheduler.hateoas;

import com.example.HealthScheduler.controller.DoctorController;
import com.example.HealthScheduler.dto.doctor.DoctorDetailsDTO;
import com.example.HealthScheduler.entity.Doctor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class DoctorAssembler extends RepresentationModelAssemblerSupport<Doctor, DoctorDetailsDTO> {


    public DoctorAssembler(Class<?> controllerClass, Class<DoctorDetailsDTO> resourceType) {
        super(controllerClass, resourceType);
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

        dto.add(
                linkTo(methodOn(DoctorController.class)
                        .getDoctorById(dto.getId())).withSelfRel()  // link: self
        );





        return null;
    }
}
