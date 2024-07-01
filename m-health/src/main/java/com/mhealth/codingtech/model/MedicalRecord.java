package com.mhealth.codingtech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dossier_medical")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "treatment_history", length = 5000)
    private String treatmentHistory;

    @Column(name = "doctors_notes", length = 5000)
    private String doctorsNotes;

    @Column(name = "diagnosis", length = 2000)
    private String diagnosis;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
}
