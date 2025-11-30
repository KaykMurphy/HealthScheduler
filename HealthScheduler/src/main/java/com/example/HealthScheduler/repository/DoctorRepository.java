package com.example.HealthScheduler.repository;

import com.example.HealthScheduler.entity.Doctor;
import com.example.HealthScheduler.enums.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByCrm(String crm);
    Page<Doctor> findByActiveTrue(Pageable pageable); // paginação

    Page<Doctor> findByActiveTrueAndSpecialization(Specialization specialization,
                                                   Pageable pageable);
    Optional<Doctor> findByIdAndActiveTrue(Long id);


}
