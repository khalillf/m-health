package com.mhealth.codingtech.dto;

import com.mhealth.codingtech.model.Prescription;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDTO {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private String notes;
    private PrescriptionDTO mapEntityToDto(Prescription prescription) {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setId(prescription.getId());
        prescriptionDTO.setDoctorId(prescription.getDoctorId());
        prescriptionDTO.setPatientId(prescription.getPatientId());
        return prescriptionDTO;
    }
}
