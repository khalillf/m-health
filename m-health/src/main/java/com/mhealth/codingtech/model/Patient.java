package com.mhealth.codingtech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "username", nullable = false, unique = true)
        private String username;

        @Column(name = "password", nullable = false)
        private String password;

        @Column(name = "first_name", nullable = false)
        private String firstName;

        @Column(name = "last_name", nullable = false)
        private String lastName;

        @Column(name = "phone", nullable = false)
        private String phone;

        @Column(name = "email", nullable = false)
        private String email;

        @Temporal(TemporalType.DATE)
        @Column(name = "date_of_birth")
        private Date dateOfBirth;


    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "doctor_patient",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Prescription> prescriptions = new ArrayList<>();

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LabResult> labResults = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();



}
