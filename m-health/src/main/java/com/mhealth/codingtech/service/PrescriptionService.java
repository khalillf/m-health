package com.mhealth.codingtech.service;

import com.mhealth.codingtech.dto.PrescriptionDTO;
import com.mhealth.codingtech.model.Prescription;
import com.mhealth.codingtech.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public PrescriptionDTO savePrescription(PrescriptionDTO prescriptionDTO) {
        Prescription prescription = mapDtoToEntity(prescriptionDTO);
        prescription = prescriptionRepository.save(prescription);
        return mapEntityToDto(prescription);
    }


    public List<PrescriptionDTO> getAllPrescription() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();
        return prescriptions.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    // Helper method to map between entity and DTO

    public Optional<PrescriptionDTO> getPrescriptionById(Long id) {
        Optional<Prescription> prescription = prescriptionRepository.findById(id);
        return prescription.map(this::mapEntityToDto);
    }

    public PrescriptionDTO updatePrescription(Long id, PrescriptionDTO prescriptionDTO) {
        Prescription prescription = mapDtoToEntity(prescriptionDTO);
        prescription.setId(id);
        prescription = prescriptionRepository.save(prescription);
        return mapEntityToDto(prescription);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

    // Helper methods to map between entity and DTO
    private Prescription mapDtoToEntity(PrescriptionDTO prescriptionDTO) {
        Prescription prescription = new Prescription();
        prescription.setDoctorId(prescriptionDTO.getDoctorId());
        prescription.setPatientId(prescriptionDTO.getPatientId());
        prescription.setNotes(prescriptionDTO.getNotes());
        return prescription;
    }

    private PrescriptionDTO mapEntityToDto(Prescription prescription) {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setId(prescription.getId());
        prescriptionDTO.setDoctorId(prescription.getDoctorId());
        prescriptionDTO.setPatientId(prescription.getPatientId());
        prescriptionDTO.setNotes(prescription.getNotes());
        return prescriptionDTO;
    }
}
