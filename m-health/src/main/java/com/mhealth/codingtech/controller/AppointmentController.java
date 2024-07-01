package com.mhealth.codingtech.controller;

import com.mhealth.codingtech.dto.AppointmentDTO;
import com.mhealth.codingtech.model.Appointment;
import com.mhealth.codingtech.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<AppointmentDTO> getAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        List<AppointmentDTO> appointmentDTOs = new ArrayList<>();
        for (Appointment appointment : appointments) {
            AppointmentDTO appointmentDTO = new AppointmentDTO(
                    appointment.getId(),
                    appointment.getDate(),
                    appointment.getTime(),
                    appointment.getNotes(),
                    appointment.getPatient().getId(),  // Assuming getId() returns the patient ID
                    appointment.getDoctor().getId()   // Assuming getId() returns the doctor ID
            );
            appointmentDTOs.add(appointmentDTO);
        }
        return appointmentDTOs;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Long id) {
        try {
            Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
            return appointment.map(app -> {
                AppointmentDTO dto = new AppointmentDTO(
                        app.getId(),
                        app.getDate(),
                        app.getTime(),
                        app.getNotes(),
                        app.getPatient().getId(),
                        app.getDoctor().getId()
                );
                return new ResponseEntity<>(dto, HttpStatus.OK);
            }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve appointment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
        if (updatedAppointment != null) {
            return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
