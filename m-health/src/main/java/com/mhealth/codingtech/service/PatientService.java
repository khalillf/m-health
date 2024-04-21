package com.mhealth.codingtech.service;

import com.mhealth.codingtech.model.Appointment;
import com.mhealth.codingtech.model.Doctor;
import com.mhealth.codingtech.model.LabResult;
import com.mhealth.codingtech.model.Patient;
import com.mhealth.codingtech.repository.AppointmentRepository;
import com.mhealth.codingtech.repository.PatientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> findPatientsByDoctorId(Long doctorId) {
        return patientRepository.findByDoctorsId(doctorId);
    }

    public List<Doctor> findDoctorsByPatientId(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        return patient.getDoctors();
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void associateDoctorWithPatient(Long doctorId, Long patientId) {
        entityManager.createNativeQuery("INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (?, ?)")
                .setParameter(1, doctorId)
                .setParameter(2, patientId)
                .executeUpdate();
    }

    public Patient findById(Long patientId) {
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        return patientOptional.orElse(null);
    }


    public List<LabResult> getAllLabResultsByPatientId(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient != null) {
            return patient.getLabResults();
        }
        return null;
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }


}
