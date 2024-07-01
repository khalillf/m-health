package com.mhealth.codingtech.service;

import com.mhealth.codingtech.model.Doctor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DoctorServiceTests {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreateDoctor() {
        // Given
        Doctor newDoctor = new Doctor();
        newDoctor.setUsername("a");
        newDoctor.setPassword("a"); // This should be raw password
        newDoctor.setFirstName("a");
        newDoctor.setLastName("a");
        newDoctor.setSpecialty("Cardiology");
        newDoctor.setPhone("1234567890");
        newDoctor.setEmail("jdoe@example.com");

        // When
        Doctor savedDoctor = doctorService.saveDoctor(newDoctor);

        // Then
        assertNotNull(savedDoctor.getId());
        assertTrue(passwordEncoder.matches("a", savedDoctor.getPassword()), "Passwords should match");
        assertEquals("a", savedDoctor.getFirstName());
        assertEquals("a", savedDoctor.getLastName());
        assertEquals("Cardiology", savedDoctor.getSpecialty());
        assertEquals("1234567890", savedDoctor.getPhone());
        assertEquals("jdoe@example.com", savedDoctor.getEmail());
    }
}
