package com.mhealth.codingtech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String notes;
    private Long patientId;
    private Long doctorId;
}
