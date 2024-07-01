package com.mhealth.codingtech.controller;

import com.mhealth.codingtech.model.Medicine;
import com.mhealth.codingtech.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping("/")
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        List<Medicine> medicines = medicineService.getAllMedicines();
        return ResponseEntity.ok(medicines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Long id) {
        Optional<Medicine> medicine = medicineService.getMedicineById(id);
        return medicine.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/")
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        Medicine createdMedicine = medicineService.saveMedicine(medicine);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMedicine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id, @RequestBody Medicine medicine) {
        if (!medicineService.getMedicineById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        medicine.setId(id);
        Medicine updatedMedicine = medicineService.saveMedicine(medicine);
        return ResponseEntity.ok(updatedMedicine);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        if (!medicineService.getMedicineById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        medicineService.deleteMedicine(id);
        return ResponseEntity.ok().build();
    }

    // Add more CRUD endpoints as needed
}
