package app.hospital.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {
    private long totalPatients;
    private long totalDoctors;
    private long totalAppointments;
    private long totalInventoryItems;
    private long totalBills;
    private long dailyAdmissions;
    private long dailyTests;
    private long dailyReports;
    private List<YearlyStatDTO> yearlyStats;
    private List<MonthlyStatDTO> monthlyStats;
    private List<AuditLogDto> recentActivities;
}
