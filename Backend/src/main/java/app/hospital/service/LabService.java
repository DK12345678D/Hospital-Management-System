package app.hospital.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import app.hospital.model.LabTest;
import app.hospital.repository.LabTestRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabService {

    private final LabTestRepository labTestRepository;

    public List<LabTest> getAllTests() {
        return labTestRepository.findAll();
    }

    public List<LabTest> getTestsByPatient(Long patientId) {
        return labTestRepository.findByPatientId(patientId);
    }

    public LabTest orderTest(LabTest test) {
        test.setTestDate(LocalDateTime.now());
        test.setStatus("PENDING");
        return labTestRepository.save(test);
    }

    public LabTest updateResult(Long id, String result) {
        LabTest test = labTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lab Test not found"));
        test.setResult(result);
        test.setStatus("COMPLETED");
        return labTestRepository.save(test);
    }

    public LabTest updateStatus(Long id, String status) {
        LabTest test = labTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lab Test not found"));
        test.setStatus(status);
        return labTestRepository.save(test);
    }

    public void deleteTest(Long id) {
        labTestRepository.deleteById(id);
    }
}
