package app.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.hospital.model.Medicine;
import app.hospital.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final MedicineRepository medicineRepository;

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public List<Medicine> searchByName(String name) {
        return medicineRepository.findByDrugNameContainingIgnoreCase(name);
    }

    public Medicine addMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public Medicine updateStock(Long id, String newStock) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
        medicine.setStock(newStock);
        return medicineRepository.save(medicine);
    }

    public Medicine updateMedicine(Long id, Medicine medicineDetails) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
        
        medicine.setDrugName(medicineDetails.getDrugName());
        medicine.setManufacturer(medicineDetails.getManufacturer());
        medicine.setStock(medicineDetails.getStock());
        medicine.setPrice(medicineDetails.getPrice());
        medicine.setExpiryDate(medicineDetails.getExpiryDate());
        
        return medicineRepository.save(medicine);
    }

    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }
}
