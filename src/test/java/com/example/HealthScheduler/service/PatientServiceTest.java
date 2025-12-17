package com.example.HealthScheduler.service;

import com.example.HealthScheduler.ModelConfig.ModelMapperConfig;
import com.example.HealthScheduler.dto.patient.PatientDetailsDTO;
import com.example.HealthScheduler.dto.patient.PatientRegistrationDTO;
import com.example.HealthScheduler.exception.BusinessException;
import com.example.HealthScheduler.repository.AppointmentRepository;
import com.example.HealthScheduler.repository.DoctorRepository;
import com.example.HealthScheduler.repository.DoctorScheduleRepository;
import com.example.HealthScheduler.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    private ModelMapper modelMapper;
    private PatientRepository patientRepository;



    // service de test
    private PatientService patientService;

    @BeforeEach
    void setup() {

        ModelMapperConfig config = new ModelMapperConfig();
        modelMapper = config.modelMapper();

        patientRepository = mock(PatientRepository.class);

        patientService = new PatientService(
                patientRepository,
                modelMapper
        );
    }

    @Test
    void sloudThrowExpectionWhenCPFExist() {

        PatientRegistrationDTO dto = new PatientRegistrationDTO(
                "Mark", "11144477735",
                LocalDate.of(2025, 1, 17),
                "99999999999", "teste@gmail.com");

        when(patientRepository.existsByCpf("11144477735")).thenReturn(true);

        assertThrows(BusinessException.class, () -> patientService.register(dto));

        verify(patientRepository, times(1)).existsByCpf("11144477735");
    }

    @Test
    void shouldThrowExceptionWhenPhoneExists() {

        PatientRegistrationDTO dto = new PatientRegistrationDTO(
                "Mark", "11144477735",
                LocalDate.of(2025, 1, 17),
                "99999999999", "teste@gmail.com");


        when(patientRepository.existsByPhone("99999999999")).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            patientService.register(dto);
        });

        assertEquals("Número de telefone já está em uso", exception.getMessage());


        verify(patientRepository, times(1)).existsByPhone("99999999999");
        verify(patientRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {

        PatientRegistrationDTO dto = new PatientRegistrationDTO(
                "Mark", "11144477735",
                LocalDate.of(2025, 1, 17),
                "99999999999", "teste@gmail.com");

        when(patientRepository.existsByEmail("teste@gmail.com")).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () ->
                patientService.register(dto));

        assertEquals("Email já está em uso", exception.getMessage());

        verify(patientRepository, times(1)).existsByEmail("teste@gmail.com");
        verify(patientRepository, never()).save(any());
    }







}
