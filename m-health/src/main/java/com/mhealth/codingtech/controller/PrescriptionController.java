package com.mhealth.codingtech.controller;

import com.mhealth.codingtech.dto.LabResultRequest;
import com.mhealth.codingtech.dto.PrescriptionDTO;
import com.mhealth.codingtech.model.LabResult;
import com.mhealth.codingtech.model.Patient;
import com.mhealth.codingtech.service.LabResultService;
import com.mhealth.codingtech.service.PatientService;
import com.mhealth.codingtech.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public ResponseEntity<List<PrescriptionDTO>> getAllPrescriptions() {
        List<PrescriptionDTO> prescriptionsDTO = prescriptionService.getAllPrescription();
        return ResponseEntity.ok(prescriptionsDTO);
    }


    @PostMapping
    public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody PrescriptionDTO prescriptionDTO) {
        PrescriptionDTO createdPrescription = prescriptionService.savePrescription(prescriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPrescription);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionDTO> updatePrescription(@PathVariable Long id, @RequestBody PrescriptionDTO prescriptionDTO) {
        if (!prescriptionService.getPrescriptionById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        PrescriptionDTO updatedPrescription = prescriptionService.updatePrescription(id, prescriptionDTO);
        return ResponseEntity.ok(updatedPrescription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        if (!prescriptionService.getPrescriptionById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        prescriptionService.deletePrescription(id);
        return ResponseEntity.ok().build();
    }

    // Add more CRUD endpoints as needed

    @RestController
    @RequestMapping("/api/lab-results")
    public static class LabResultController {

        @Autowired
        private LabResultService labResultService;

        @Autowired
        private PatientService patientService;

        @GetMapping("/")
        public List<LabResult> getAllLabResults() {
            return labResultService.getAllLabResults();
        }

        @GetMapping("/{id}")
        public ResponseEntity<LabResult> getLabResultById(@PathVariable Long id) {
            return labResultService.getLabResultById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping("/")
        public ResponseEntity<LabResult> createLabResult(@RequestBody LabResultRequest labResultRequest) {
            // Check if the patientId is provided
            if (labResultRequest.getId_patient() == null) {
                return ResponseEntity.badRequest().build();
            }

            // Retrieve the patient using the provided patientId
            Long patientId = labResultRequest.getId_patient();
            Patient patient = patientService.findById(patientId);

            // Check if the patient exists
            if (patient == null) {
                return ResponseEntity.notFound().build();
            }

            // Create a new LabResult object
            LabResult labResult = new LabResult();
            labResult.setPatient(patient);
            labResult.setTestName(labResultRequest.getTestName());
            labResult.setResultValue(labResultRequest.getResultValue());
            labResult.setResultDate(LocalDate.parse(labResultRequest.getResultDate()));
            labResult.setNotes(labResultRequest.getNotes());

            // Save the LabResult object
            LabResult savedLabResult = labResultService.createLabResult(labResult);
            return ResponseEntity.ok(savedLabResult);
        }



        @PutMapping("/{id}")
        public ResponseEntity<LabResult> updateLabResult(@PathVariable Long id, @RequestBody LabResult labResult) {
            LabResult updatedLabResult = labResultService.updateLabResult(id, labResult);
            return ResponseEntity.ok(updatedLabResult);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteLabResult(@PathVariable Long id) {
            labResultService.deleteLabResult(id);
            return ResponseEntity.ok().build();
        }
    }
}
