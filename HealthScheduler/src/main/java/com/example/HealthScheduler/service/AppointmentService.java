package com.example.HealthScheduler.service;

import com.example.HealthScheduler.dto.appointment.AppointmentDetailsDTO;
import com.example.HealthScheduler.dto.appointment.AppointmentRegistrationDTO;
import com.example.HealthScheduler.entity.Appointment;
import com.example.HealthScheduler.entity.Doctor;
import com.example.HealthScheduler.entity.Patient;
import com.example.HealthScheduler.enums.AppointmentStatus;
import com.example.HealthScheduler.exception.BusinessException;
import com.example.HealthScheduler.exception.DoctorNotFoundException;
import com.example.HealthScheduler.exception.PatientNotFoundException;
import com.example.HealthScheduler.repository.AppointmentRepository;
import com.example.HealthScheduler.repository.DoctorRepository;
import com.example.HealthScheduler.repository.DoctorScheduleRepository;
import com.example.HealthScheduler.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final ModelMapper modelMapper;


    // criação da consulta
    @Transactional
    public AppointmentDetailsDTO create(Long id, AppointmentRegistrationDTO dto) {

        Doctor doctor = doctorRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Nenhum médico encontrado com o ID: " + id
                ));

        Patient patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new PatientNotFoundException(
                        "Nenhum paciente encontrado com o ID: " + dto.patientId()
                ));

        LocalDateTime startTime = dto.appointmentDate();
        LocalDateTime endTime = startTime.plusMinutes(dto.durationMinutes());

        if (endTime.isAfter(startTime.withHour(18).withMinute(0))) {
            throw new BusinessException("A consulta termina fora do horário permitido");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setCreatedAt(startTime);
        appointment.setDurationMinutes(dto.durationMinutes());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        appointment = appointmentRepository.save(appointment);

        return modelMapper.map(appointment, AppointmentDetailsDTO.class);
    }



}
