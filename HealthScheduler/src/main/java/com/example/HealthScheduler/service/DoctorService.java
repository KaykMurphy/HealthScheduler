package com.example.HealthScheduler.service;

import com.example.HealthScheduler.dto.doctor.DoctorDetailsDTO;
import com.example.HealthScheduler.dto.doctor.DoctorRegistrationDTO;
import com.example.HealthScheduler.dto.doctor.DoctorUpdateDTO;
import com.example.HealthScheduler.entity.Doctor;
import com.example.HealthScheduler.enums.Specialization;
import com.example.HealthScheduler.exception.BusinessException;
import com.example.HealthScheduler.exception.DoctorNotFoundException;
import com.example.HealthScheduler.repository.AppointmentRepository;
import com.example.HealthScheduler.repository.DoctorRepository;
import com.example.HealthScheduler.repository.DoctorScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public DoctorDetailsDTO register(DoctorRegistrationDTO dto){

        if (doctorRepository.existsByCrm(dto.crm())) {
            throw new BusinessException("CRM já cadastrado");
        }

        // DTO > Entity
        Doctor doctor = modelMapper.map(dto, Doctor.class);

        doctorRepository.save(doctor);

        // Entity > DTO
        return modelMapper.map(doctor, DoctorDetailsDTO.class);
    }

    public DoctorDetailsDTO findById(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Médico não encontrado"));

        return modelMapper.map(doctor, DoctorDetailsDTO.class);
    }

    public Page<DoctorDetailsDTO> findAll(Pageable pageable){

        Page<Doctor> page = doctorRepository
                .findByActiveTrue(pageable);

        return page.map(doctor -> modelMapper.map(doctor, DoctorDetailsDTO.class));
    }

    public Page<DoctorDetailsDTO> findBySpecialization(Specialization specialization, Pageable pageable){

        Page<Doctor> page = doctorRepository
                .findByActiveTrueAndSpecialization(specialization, pageable);

        return page.map(doctor -> modelMapper.map(doctor, DoctorDetailsDTO.class));

    }

    @Transactional
    public DoctorDetailsDTO update(Long id, DoctorUpdateDTO dto){

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Médico não encontrado"));


        // Atualiza somente os campos informados no DTO (partial update)
        if (dto.name() != null){
            doctor.setName(dto.name());
        }

        if (dto.email() != null){
            doctor.setEmail(dto.email());
        }

        if (dto.phone() != null){
            doctor.setPhone(dto.phone());
        }

        return modelMapper.map(doctor, DoctorDetailsDTO.class);
    }


    @Transactional
    public void deactivate(Long id){

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Médico não encontrado"));

        // Verifica se existe consulta futura
        boolean hasFutureAppointment = appointmentRepository
                .existsByDoctorIdAndAppointmentDateAfter(id, LocalDateTime.now());


        if (hasFutureAppointment){
            throw new BusinessException("Não é possível desativar o médico: existem consultas futuras agendadas.");
        }

        doctor.setActive(false);
    }

    @Transactional
    public void activate(Long id){

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Médico não encontrado"));

        if (!doctor.isActive()) {
            doctor.setActive(true);
        }

        doctorRepository.save(doctor);
    }
}
