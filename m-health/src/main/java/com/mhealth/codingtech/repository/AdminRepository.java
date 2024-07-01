package com.mhealth.codingtech.repository;

import com.mhealth.codingtech.model.Admin;
import com.mhealth.codingtech.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByUsername(String username);
    Admin findByUsernameAndPassword(String username, String password);
}
