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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        LocalDateTime endOfBusiness = startTime.toLocalDate().atTime(18, 0);
        if (endTime.isAfter(endOfBusiness)) {
            throw new BusinessException("A consulta termina fora do horário permitido");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);              // <- importante
        appointment.setDurationMinutes(dto.durationMinutes());
        appointment.setCreatedAt(LocalDateTime.now()); // <- correto para createdAt
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        appointment = appointmentRepository.save(appointment);

        return modelMapper.map(appointment, AppointmentDetailsDTO.class);
    }

    @Transactional
    public AppointmentDetailsDTO findById(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "Nenhuma consulta encontrada com o ID: " + id
                ));

        return modelMapper.map(appointment, AppointmentDetailsDTO.class);
    }


    public Page<AppointmentDetailsDTO> findAll(Pageable pageable){

        Page<Appointment> page = appointmentRepository.findAll(pageable);

        return page.map(appointment ->
                modelMapper.map(appointment, AppointmentDetailsDTO.class));

    }

    public Page<AppointmentDetailsDTO> findByPatient(Long patientId, Pageable pageable){

        patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(
                        "Paciente não encontrado com o ID: " + patientId
                ));

        Page<Appointment> page = appointmentRepository.findByPatientId(patientId, pageable);

        return page.map(appointment ->
                modelMapper.map(appointment, AppointmentDetailsDTO.class)
        );

    }

    public Page<AppointmentDetailsDTO> findByDoctor(Long doctorId, Pageable pageable){

        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Médico não encontrado com o ID: "+doctorId
                ));

        Page<Appointment> page = appointmentRepository.findByDoctorId(doctorId, pageable);

        return page.map(appointment ->
                modelMapper.map(appointment, AppointmentDetailsDTO.class)
        );
    }


    public Page<AppointmentDetailsDTO> findByStatus(AppointmentStatus status, Pageable pageable){


        Page<Appointment> page = appointmentRepository.findByStatus(status, pageable);

        return page.map(
                appointment -> modelMapper.map(appointment, AppointmentDetailsDTO.class)
        );
    }


    @Transactional
    public AppointmentDetailsDTO confirm(Long id){


        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "Nenhuma consulta encontrada com o ID: "+id
                ));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new BusinessException("Somente consultas agendadas podem ser confirmadas");
        }


        if (appointment.getAppointmentDate().isBefore(LocalDateTime.now())){
            throw new BusinessException("Não é possível confirmar uma consulta que já ocorreu");
        }

        appointment.setStatus(AppointmentStatus.CONFIRMED);

        //optimistic locking atua no flush
        appointment = appointmentRepository.save(appointment);

        return modelMapper.map(appointment, AppointmentDetailsDTO.class);


    }


    // TODO implementar cancel


    // TODO implementar complete



}
