package com.example.HealthScheduler.service;

import com.example.HealthScheduler.dto.schedule.AvailableSlotDTO;
import com.example.HealthScheduler.dto.schedule.DoctorScheduleDTO;
import com.example.HealthScheduler.entity.Doctor;
import com.example.HealthScheduler.entity.DoctorSchedule;
import com.example.HealthScheduler.exception.BusinessException;
import com.example.HealthScheduler.exception.DoctorNotFoundException;
import com.example.HealthScheduler.repository.DoctorRepository;
import com.example.HealthScheduler.repository.DoctorScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import com.example.HealthScheduler.enums.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    //definir agendamento semanal
    @Transactional
    public List<DoctorScheduleDTO> setWeeklySchedule(Long doctorId, List<DoctorScheduleDTO> dtos) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new DoctorNotFoundException("Médico não encontrado com o ID: " + doctorId)
                );

        doctorScheduleRepository.deleteByDoctorId(doctorId);

        List<DoctorSchedule> schedules = dtos.stream().map(dto -> {

            if (dto.startTime().isAfter(dto.endTime())) {
                throw new BusinessException(
                        "Horário inicial não pode ser após o horário final");
            }

            DoctorSchedule doctorSchedule = new DoctorSchedule();
            modelMapper.map(dto, doctorSchedule);

            doctorSchedule.setDoctor(doctor); // associa a agenda com o doctor

            return doctorSchedule;

        }).toList();

        doctorScheduleRepository.saveAll(schedules);

        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, DoctorScheduleDTO.class))
                .toList();
    }



    //mostra programação semanal
    @Transactional
    public List<DoctorScheduleDTO> getWeeklySchedule(Long doctorId){

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new DoctorNotFoundException("Médico não encontrado com o ID: " + doctorId)
                );

        // agendas do doctor
        List<DoctorSchedule> schedules =
                doctorScheduleRepository.findByDoctorId(doctorId);


        // orderna por dia da semana
        schedules.sort(Comparator.comparing(DoctorSchedule::getDayOfWeek));

        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, DoctorScheduleDTO.class))
                .toList();
    }


    //TODO : adicionar a feature para ver os horários
}
