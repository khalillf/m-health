package com.mhealth.codingtech.repository;

import com.mhealth.codingtech.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByUsername(String username);
    Doctor findByUsernameAndPassword(String username, String password);
}
