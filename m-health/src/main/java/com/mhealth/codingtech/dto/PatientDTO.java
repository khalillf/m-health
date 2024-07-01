package com.mhealth.codingtech.dto;

import com.mhealth.codingtech.model.Patient;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PatientDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Date dateOfBirth;

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.username = patient.getUsername();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.phone = patient.getPhone();
        this.email = patient.getEmail();
        this.dateOfBirth = patient.getDateOfBirth();
    }
}
