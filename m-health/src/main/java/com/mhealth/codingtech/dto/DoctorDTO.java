package com.mhealth.codingtech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String specialty;
    private String phone;
    private String email;

    // Getters and setters
}
