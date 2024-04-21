package com.mhealth.codingtech.controller;

import com.mhealth.codingtech.dto.AppointmentDTO;
import com.mhealth.codingtech.model.Appointment;
import com.mhealth.codingtech.model.Doctor;
import com.mhealth.codingtech.model.LabResult;
import com.mhealth.codingtech.model.Patient;
import com.mhealth.codingtech.service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientService.savePatient(patient);
        return ResponseEntity.ok(createdPatient);
    }
    @GetMapping("/{patientId}/doctors")
    public List<Doctor> getDoctorsByPatientId(@PathVariable Long patientId) {
        // Call service method to find doctors by patient ID
        return patientService.findDoctorsByPatientId(patientId);
    }
    @GetMapping("/")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Patient> getPatientsByDoctorId(@PathVariable Long doctorId) {
        return patientService.findPatientsByDoctorId(doctorId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Patient existingPatient = patientService.getPatientById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + id));
        // Assume setters copy the properties from the request to the existing entity
        existingPatient.setUsername(patient.getUsername());
        existingPatient.setPassword(patient.getPassword());
        existingPatient.setFirstName(patient.getFirstName());
        existingPatient.setLastName(patient.getLastName());
        existingPatient.setPhone(patient.getPhone());
        existingPatient.setEmail(patient.getEmail());
        existingPatient.setDateOfBirth(patient.getDateOfBirth());
        patientService.updatePatient(existingPatient);
        return ResponseEntity.ok(existingPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }


        //relations complex


    @PostMapping("/{patientId}/associate-doctor/{doctorId}")
    public ResponseEntity<Void> associateDoctorWithPatient(@PathVariable Long patientId, @PathVariable Long doctorId) {
        patientService.associateDoctorWithPatient(doctorId, patientId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{patientId}/lab-results")
    public ResponseEntity<List<LabResult>> getAllLabResultsByPatientId(@PathVariable Long patientId) {
        List<LabResult> labResults = patientService.getAllLabResultsByPatientId(patientId);
        if (labResults != null) {
            return new ResponseEntity<>(labResults, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{patientId}/appointments")
    public List<AppointmentDTO> getAppointmentsByPatientId(@PathVariable Long patientId) {
        List<Appointment> appointments = patientService.getAppointmentsByPatientId(patientId);
        List<AppointmentDTO> appointmentDTOs = new ArrayList<>();
        for (Appointment appointment : appointments) {
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            BeanUtils.copyProperties(appointment, appointmentDTO);
            appointmentDTO.setPatientId(appointment.getPatient().getId());
            appointmentDTO.setDoctorId(appointment.getDoctor().getId());
            appointmentDTOs.add(appointmentDTO);
        }
        return appointmentDTOs;
    }


}
