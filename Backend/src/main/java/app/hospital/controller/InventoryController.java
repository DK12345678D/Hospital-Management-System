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

import app.hospital.model.Medicine;
import app.hospital.service.InventoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/medicines")
    public List<Medicine> getAllMedicines() {
        return inventoryService.getAllMedicines();
    }

    @GetMapping("/search")
    public List<Medicine> search(@RequestParam String name) {
        return inventoryService.searchByName(name);
    }

    @PostMapping("/medicines/add")
    public Medicine addMedicine(@RequestBody Medicine medicine) {
        return inventoryService.addMedicine(medicine);
    }

    @PutMapping("/medicines/{id}/stock")
    public Medicine updateStock(@PathVariable Long id, @RequestParam String stock) {
        return inventoryService.updateStock(id, stock);
    }

    @DeleteMapping("/medicines/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        inventoryService.deleteMedicine(id);
        return ResponseEntity.noContent().build();
    }
}
