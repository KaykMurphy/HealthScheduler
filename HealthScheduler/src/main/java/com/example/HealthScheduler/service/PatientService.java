package com.example.HealthScheduler.service;

import com.example.HealthScheduler.dto.patient.PatientDetailsDTO;
import com.example.HealthScheduler.dto.patient.PatientRegistrationDTO;
import com.example.HealthScheduler.dto.patient.PatientUpdateDTO;
import com.example.HealthScheduler.entity.Patient;
import com.example.HealthScheduler.exception.BusinessException;
import com.example.HealthScheduler.exception.PatientNotFoundException;
import com.example.HealthScheduler.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private ModelMapper modelMapper;


    @Transactional
    public PatientDetailsDTO register(PatientRegistrationDTO dto){

        if (patientRepository.existsByCpf(dto.cpf())){
            throw new BusinessException(("CPF já está em uso"));
        }

        if (patientRepository.existsByPhone(dto.phone())){
            throw new BusinessException("Número de telefone já está em uso");
        }

        if (patientRepository.existsByEmail(dto.email())){
            throw new BusinessException("Email já está em uso");
        }

        Patient patient = modelMapper.map(dto, Patient.class);

        patientRepository.save(patient);

        return modelMapper.map(patient, PatientDetailsDTO.class);
    }


    public PatientDetailsDTO findById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(
                        "Paciente não encontrado com o ID: " + id
                ));

        return modelMapper.map(patient, PatientDetailsDTO.class);
    }



    public Page<PatientDetailsDTO> findAll(Pageable pageable){

        Page<Patient> page = patientRepository
                .findAll(pageable);

        return page.map(patient -> modelMapper.map(patient, PatientDetailsDTO.class));
    }



    public PatientDetailsDTO update(Long id, PatientUpdateDTO dto){

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(
                        "Paciente não encontrado com o ID: "+ id
                      ));

        if (dto.name() != null) {
            patient.setName(dto.name());
        }

        if (dto.email() != null) {
            patient.setEmail(dto.email());
        }

        if (dto.phone() != null) {
            patient.setPhone(dto.phone());
        }

        patientRepository.save(patient);

        return modelMapper.map(patient, PatientDetailsDTO.class);
    }


    @Transactional
    public void delete(Long id) {

        patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(
                        "Paciente não encontrado com o ID: " + id
                ));

        patientRepository.deleteById(id);
    }


}
































