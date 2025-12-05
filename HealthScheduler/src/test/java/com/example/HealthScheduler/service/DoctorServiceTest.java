package com.example.HealthScheduler.service;

import com.example.HealthScheduler.ModelConfig.ModelMapperConfig;
import com.example.HealthScheduler.dto.doctor.DoctorDetailsDTO;
import com.example.HealthScheduler.dto.doctor.DoctorRegistrationDTO;
import com.example.HealthScheduler.dto.doctor.DoctorUpdateDTO;
import com.example.HealthScheduler.entity.Doctor;
import com.example.HealthScheduler.enums.Specialization;
import com.example.HealthScheduler.exception.BusinessException;
import com.example.HealthScheduler.repository.AppointmentRepository;
import com.example.HealthScheduler.repository.DoctorRepository;
import com.example.HealthScheduler.repository.DoctorScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {

    //mocks
    private DoctorRepository doctorRepository;
    private DoctorScheduleRepository doctorScheduleRepository;
    private AppointmentRepository appointmentRepository;
    private ModelMapper modelMapper;

    // service de test
    private DoctorService doctorService;

    @BeforeEach
    void setup() {
        doctorRepository = mock(DoctorRepository.class);
        doctorScheduleRepository = mock(DoctorScheduleRepository.class);
        appointmentRepository = mock(AppointmentRepository.class);

        ModelMapperConfig config = new ModelMapperConfig();
        modelMapper = config.modelMapper();

        doctorService  = new DoctorService(
                doctorRepository,
                doctorScheduleRepository,
                appointmentRepository,
                modelMapper
        );
    }

    @Test
    void shouldThrowExceptionWhenCRMExists() {

        DoctorRegistrationDTO dto =  new DoctorRegistrationDTO("Elon", "213451", Specialization.CARDIOLOGY, "2131111", "email@test.com");

        when(doctorRepository.existsByCrm("213451")).thenReturn(true);

        assertThrows(BusinessException.class, () -> doctorService.register(dto));

        verify(doctorRepository, times(1)).existsByCrm("213451");
    }

    @Test
    void shouldRegisterDoctorSuccessfully() {

        DoctorRegistrationDTO dto = new DoctorRegistrationDTO("Elon", "213451", Specialization.CARDIOLOGY, "2131111", "email@test.com");

        when(doctorRepository.existsByCrm("213451")).thenReturn(false);

        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DoctorDetailsDTO result = doctorService.register(dto);

        assertEquals("Elon", result.getName());
        assertEquals("email@test.com", result.getEmail());


        verify(doctorRepository).save(any(Doctor.class));
    }

    @Test
    void shouldUpdateNameAndEmailAndPhoneWhenProvided() {

        var doctor = new Doctor();
        doctor.setId(1L);
        doctor.setEmail("email@gmail.com");
        doctor.setPhone("119990000");
        doctor.setName("doctor");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        var dto = new DoctorUpdateDTO("Roby", "1199999999", "dev@gmail.com");

        var result = doctorService.update(1L, dto);

        assertEquals("Roby", result.getName());
        assertEquals("1199999999", result.getPhone());
        assertEquals("dev@gmail.com", result.getEmail());

        verify(doctorRepository).save(any(Doctor.class));



    }
}
