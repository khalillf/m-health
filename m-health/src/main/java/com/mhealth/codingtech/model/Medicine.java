package com.mhealth.codingtech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    // You might want to include other fields such as dosage, interactions, etc.

    @ManyToMany(mappedBy = "medicines")
    Set<Prescription> prescriptions; // Reverse mapping
}
