package com.example.HealthScheduler.repository;

import com.example.HealthScheduler.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);

    Optional<Patient> findByCpf(String cpf);
}
