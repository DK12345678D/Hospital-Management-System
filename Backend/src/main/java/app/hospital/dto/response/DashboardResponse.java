package app.hospital.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {
    private long totalCandidates;
    private long onlineCandidates;
    private long totalQuestions;
    private long totalTests;
    private long totalSubjects;
    private long totalCategories;
    private long registrationsToday;
    private long testsStartedToday;
    private long reportsGeneratedToday;
    private List<YearlyStatDTO> yearlyStats;
    private List<MonthlyStatDTO> monthlyStats;
    private List<AuditLogDto> recentActivities;
}
