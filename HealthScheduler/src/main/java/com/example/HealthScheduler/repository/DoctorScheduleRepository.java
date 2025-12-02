package com.example.HealthScheduler.repository;

import com.example.HealthScheduler.entity.Doctor;
import com.example.HealthScheduler.entity.DoctorSchedule;
import com.example.HealthScheduler.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    List<DoctorSchedule> findByDoctorId(Long id);

    Optional<DoctorSchedule> findByDoctorAndDayOfWeek(Long doctorId, DayOfWeek day);

    boolean deleteByDoctorId(Long doctorId);

    Optional<Doctor> deleteDoctorById(Long doctorId);


}
