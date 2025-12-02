package com.example.HealthScheduler.entity;

import com.example.HealthScheduler.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "tb_patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(length = 20, unique = true)
    private String phone;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 200)
    private String street;

    @Column(length = 10)
    private String number;

    @Column(length = 100)
    private String complement;

    @Column(length = 100)
    private String neighborhood;

    @Column(length = 100)
    private String city;

    @Column(length = 2)
    private String state;

    @Column(length = 8)
    private String zipCode;

    @CreationTimestamp
    private LocalDateTime createdAt;

/*    @OneToMany(mappedBy = "patient")
    private List<AppointmentStatus> appointment;*/
}
