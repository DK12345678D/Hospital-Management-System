package app.hospital.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import app.hospital.model.Bill;
import app.hospital.repository.BillRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillRepository billRepository;

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public List<Bill> getBillsByPatient(Long patientId) {
        return billRepository.findByPatientId(patientId);
    }

    public Bill generateBill(Bill bill) {
        bill.setBillDate(LocalDateTime.now());
        if (bill.getStatus() == null) {
            bill.setStatus("UNPAID");
        }
        return billRepository.save(bill);
    }

    public Bill markAsPaid(Long id, String paymentMethod) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        bill.setStatus("PAID");
        bill.setPaymentMethod(paymentMethod);
        return billRepository.save(bill);
    }

    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }
}
