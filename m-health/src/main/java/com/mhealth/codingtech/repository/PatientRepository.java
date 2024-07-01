package com.mhealth.codingtech.repository;

import com.mhealth.codingtech.model.Doctor;
import com.mhealth.codingtech.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDoctorsId(Long doctorId);
    Patient findByUsername(String username);
    Patient findByUsernameAndPassword(String username, String password);

}
