package app.hospital.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.hospital.model.Admission;
import app.hospital.repository.AdmissionRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ipd")
@RequiredArgsConstructor
public class AdmissionController {

    private final AdmissionRepository admissionRepository;

    @GetMapping("/admissions")
    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll();
    }

    @GetMapping("/patient/{patientId}")
    public List<Admission> getAdmissionsByPatient(@PathVariable Long patientId) {
        return admissionRepository.findByPatientId(patientId);
    }

    @PostMapping("/admit")
    public Admission admitPatient(@RequestBody Admission admission) {
        admission.setAdmissionDate(LocalDateTime.now());
        admission.setStatus("ADMITTED");
        return admissionRepository.save(admission);
    }

    @PutMapping("/{id}/discharge")
    public Admission dischargePatient(@PathVariable Long id, @RequestBody String summary) {
        Admission admission = admissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission not found"));
        admission.setDischargeDate(LocalDateTime.now());
        admission.setDischargeSummary(summary);
        admission.setStatus("DISCHARGED");
        return admissionRepository.save(admission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmission(@PathVariable Long id) {
        admissionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
