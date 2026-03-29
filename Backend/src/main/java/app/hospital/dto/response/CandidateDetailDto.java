package app.hospital.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDetailDto {
    private Long candidateId;
    private String candidateName;
    private String enrollment;
    private String email;
    private String mobile;
    private String groupName;
    private List<CandidateTestDetailDto> tests;
}
