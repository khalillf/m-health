package com.mhealth.codingtech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "lab_results")
public class LabResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Patient patient;

    @Column(name = "test_name", nullable = false)
    private String testName;

    @Column(name = "result_value")
    private String resultValue;

    @Column(name = "result_date")
    private LocalDate resultDate;

    @Column(name = "notes")
    private String notes;
}
