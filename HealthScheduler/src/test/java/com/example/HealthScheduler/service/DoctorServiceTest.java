package com.example.HealthScheduler.service;

import com.example.HealthScheduler.ModelConfig.ModelMapperConfig;
import com.example.HealthScheduler.dto.doctor.DoctorDetailsDTO;
import com.example.HealthScheduler.dto.doctor.DoctorRegistrationDTO;
import com.example.HealthScheduler.entity.Doctor;
import com.example.HealthScheduler.enums.Specialization;
import com.example.HealthScheduler.exception.BusinessException;
import com.example.HealthScheduler.repository.AppointmentRepository;
import com.example.HealthScheduler.repository.DoctorRepository;
import com.example.HealthScheduler.repository.DoctorScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor
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

        var dto =  new DoctorRegistrationDTO("Elon", "213451", Specialization.CARDIOLOGY, "2131111", "email@test.com");

        when(doctorRepository.existsByCrm("213451")).thenReturn(true);

        assertThrows(BusinessException.class, () -> doctorService.register(dto));

        verify(doctorRepository, times(1)).existsByCrm("213451");
    }

    @Test
    void shouldRegisterDoctorSuccessfully() {

        var dto = new DoctorRegistrationDTO("Elon", "213451", Specialization.CARDIOLOGY, "2131111", "email@test.com");

        when(doctorRepository.existsByCrm("213451")).thenReturn(false);

        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DoctorDetailsDTO result = doctorService.register(dto);

        assertEquals("Elon", result.getName());
        assertEquals("email@test.com", result.getEmail());


        verify(doctorRepository).save(any(Doctor.class));
    }
}
