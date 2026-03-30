package app.hospital.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.hospital.model.Prescription;
import app.hospital.service.PrescriptionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/prescriptions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetMapping
    public List<Prescription> getAll() {
        return prescriptionService.getAllPrescriptions();
    }

    @GetMapping("/patient/{id}")
    public List<Prescription> getByPatient(@PathVariable Long id) {
        return prescriptionService.getByPatient(id);
    }

    @GetMapping("/doctor/{id}")
    public List<Prescription> getByDoctor(@PathVariable Long id) {
        return prescriptionService.getByDoctor(id);
    }

    @PostMapping("/add")
    public Prescription add(@RequestBody Prescription prescription) {
        return prescriptionService.addPrescription(prescription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build();
    }
}
