package com.mhealth.codingtech.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "prescription_date")
    private LocalDate prescriptionDate;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "prescription_medicine",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private List<Medicine> medicines;

    @Column(name = "notes")
    private String notes;


    public Long getDoctorId() {
        return doctor != null ? doctor.getId() : null;
    }

    public void setDoctorId(Long doctorId) {
        // You can ignore setting doctorId as it's derived from the doctor object
    }

    public Long getPatientId() {
        return patient != null ? patient.getId() : null;
    }

    public void setPatientId(Long patientId) {
        // Set the patientId directly if necessary
        this.patient = new Patient();
        this.patient.setId(patientId);
    }

}
