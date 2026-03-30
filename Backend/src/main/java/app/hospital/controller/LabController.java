package app.hospital.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.hospital.model.LabTest;
import app.hospital.service.LabService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/lab")
@RequiredArgsConstructor
public class LabController {

    private final LabService labService;

    @GetMapping("/tests")
    public List<LabTest> getAllTests() {
        return labService.getAllTests();
    }

    @GetMapping("/patient/{patientId}")
    public List<LabTest> getTestsByPatient(@PathVariable Long patientId) {
        return labService.getTestsByPatient(patientId);
    }

    @PostMapping("/tests/order")
    public LabTest orderTest(@RequestBody LabTest test) {
        return labService.orderTest(test);
    }

    @PutMapping("/tests/{id}/result")
    public LabTest updateResult(@PathVariable Long id, @RequestBody String result) {
        return labService.updateResult(id, result);
    }

    @PutMapping("/tests/{id}/status")
    public LabTest updateStatus(@PathVariable Long id, @RequestParam String status) {
        return labService.updateStatus(id, status);
    }

    @DeleteMapping("/tests/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        labService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
}
