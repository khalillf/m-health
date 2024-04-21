package com.mhealth.codingtech.controller;

import com.mhealth.codingtech.dto.AppointmentDTO;
import com.mhealth.codingtech.model.Appointment;
import com.mhealth.codingtech.model.Doctor;
import com.mhealth.codingtech.service.DoctorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        return doctor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor createdDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        if (!doctorService.getDoctorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        doctor.setId(id);
        Doctor updatedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        if (!doctorService.getDoctorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{doctorId}/appointments")
    public List<AppointmentDTO> getAppointmentsByDoctorId(@PathVariable Long doctorId) {
        List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(doctorId);
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
