package com.mhealth.codingtech.service;

import com.mhealth.codingtech.model.Admin;
import com.mhealth.codingtech.model.Doctor;
import com.mhealth.codingtech.model.Patient;
import com.mhealth.codingtech.repository.AdminRepository;
import com.mhealth.codingtech.repository.DoctorRepository;
import com.mhealth.codingtech.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Map<String, Object> authenticateUser(String username, String password) {
        Doctor doctor = doctorRepository.findByUsernameAndPassword(username, password);
        if (doctor != null) {
            return createUserMap("doctor", doctor.getId());
        }

        Patient patient = patientRepository.findByUsernameAndPassword(username, password);
        if (patient != null) {
            return createUserMap("patient", patient.getId());
        }

        Admin admin = adminRepository.findByUsernameAndPassword(username, password);
        if (admin != null) {
            return createUserMap("admin", admin.getId());
        }

        return null; // User not found
    }

    private Map<String, Object> createUserMap(String userType, Long userId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userType", userType);
        resultMap.put("userId", userId);
        return resultMap;
    }
}
