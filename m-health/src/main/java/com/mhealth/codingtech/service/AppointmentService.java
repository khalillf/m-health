package com.mhealth.codingtech.service;

import com.mhealth.codingtech.model.Appointment;
import com.mhealth.codingtech.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isPresent()) {
            Appointment existingAppointment = optionalAppointment.get();
            existingAppointment.setDate(appointmentDetails.getDate());
            existingAppointment.setTime(appointmentDetails.getTime());
            existingAppointment.setNotes(appointmentDetails.getNotes());
            return appointmentRepository.save(existingAppointment);
        } else {
            // Handle not found scenario
            return null;
        }
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
