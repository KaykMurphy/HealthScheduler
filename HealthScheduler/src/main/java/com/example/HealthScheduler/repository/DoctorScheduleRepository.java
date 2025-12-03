package com.example.HealthScheduler.repository;

import com.example.HealthScheduler.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    List<DoctorSchedule> findByDoctorId(Long id);

    @Modifying
    @Query("DELETE FROM DoctorSchedule ds WHERE ds.doctor.id = :doctorId")
    void deleteByDoctorId(@Param("doctorId") Long doctorId);
}