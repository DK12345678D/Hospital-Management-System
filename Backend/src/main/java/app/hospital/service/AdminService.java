package app.hospital.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import app.hospital.dto.response.DashboardResponse;
import app.hospital.repository.AdminRepository;
import app.hospital.repository.AppointmentRepository;
import app.hospital.repository.BillRepository;
import app.hospital.repository.DoctorRepository;
import app.hospital.repository.MedicineRepository;
import app.hospital.repository.PatientRepository;
import lombok.RequiredArgsConstructor;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicineRepository medicineRepository;
    private final BillRepository billRepository;

    public AdminService(AdminRepository adminRepository,
                        PatientRepository patientRepository,
                        DoctorRepository doctorRepository,
                        AppointmentRepository appointmentRepository,
                        MedicineRepository medicineRepository,
                        BillRepository billRepository) {
        this.adminRepository = adminRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicineRepository = medicineRepository;
        this.billRepository = billRepository;
    }

    public DashboardResponse getDashboardStats() {
        return DashboardResponse.builder()
                .totalPatients(patientRepository.count())
                .totalDoctors(doctorRepository.count())
                .totalAppointments(appointmentRepository.count())
                .totalInventoryItems(medicineRepository.count())
                .totalBills(billRepository.count())
                .dailyAdmissions(5) // Mock or real logic
                .dailyTests(12)
                .dailyReports(8)
                .recentActivities(new ArrayList<>())
                .monthlyStats(new ArrayList<>())
                .yearlyStats(new ArrayList<>())
                .build();
    }
}
