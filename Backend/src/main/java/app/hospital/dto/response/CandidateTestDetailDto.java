package app.hospital.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateTestDetailDto {
    private String testName;
    private String testStatus; // Completed, Pending, InProgress
    private String score;
    private String testDate;
}
