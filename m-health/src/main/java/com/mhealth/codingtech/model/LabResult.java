package com.mhealth.codingtech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lab_results")
public class LabResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnore
    private Patient patient;

    @Column(name = "test_name", nullable = false)
    private String testName;

    @Column(name = "result_value")
    private String resultValue;

    @Column(name = "result_date")
    private LocalDate resultDate;

    @Column(name = "notes")
    private String notes;

    public Long getPatientId() {
        if (patient != null) {
            return patient.getId();
        } else {
            return null; // or throw an exception, depending on your requirements
        }
    }

}
