package com.example.HealthScheduler.repository;

import com.example.HealthScheduler.entity.Appointment;

import com.example.HealthScheduler.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);
    Page<Appointment> findByDoctorId(Long doctorId, Pageable pageable);
    Page<Appointment> findByStatus(AppointmentStatus appointmentStatus, Pageable pageable);

    List<Appointment> findByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);


    boolean existsByDoctorIdAndAppointmentDateAfter(Long doctorId, LocalDateTime now);


}
