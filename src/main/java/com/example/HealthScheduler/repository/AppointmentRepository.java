package com.example.HealthScheduler.repository;

import com.example.HealthScheduler.entity.Appointment;
import com.example.HealthScheduler.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);

    Page<Appointment> findByDoctorId(Long doctorId, Pageable pageable);

    Page<Appointment> findByStatus(AppointmentStatus appointmentStatus, Pageable pageable);

    List<Appointment> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a " +
            "WHERE a.doctor.id = :doctorId AND a.startTime > :now")
    boolean existsByDoctorIdAndStartTimeAfter(@Param("doctorId") Long doctorId,
                                              @Param("now") LocalDateTime now);
}