package app.hospital.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import app.hospital.model.Candidate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CandidateResponse {

    @JsonProperty("S.No.")
    private Long sNo;

    @JsonProperty("Candidate Name")
    private String candidateName;

    @JsonProperty("Enrollment No")
    private String enrollmentNo;

    @JsonProperty("E-mail")
    private String email;

    @JsonProperty("Mobile No.")
    private String mobileNo;

    @JsonProperty("Group")
    private String group;

    @JsonProperty("Enroll")
    private String enroll;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Payment Status")
    private String paymentStatus;

    @JsonProperty("Reg. Date")
    private String regDate;

    // A helper mapper method
    public static CandidateResponse fromEntity(Candidate candidate, Long index) {
        return CandidateResponse.builder()
                .sNo(index)
                .candidateName(candidate.getCandidateName())
                .enrollmentNo(candidate.getEnrollmentNumber())
                .email(candidate.getEmail())
                .mobileNo(candidate.getMobileNumber())
                .group(candidate.getGroupId() != null ? String.valueOf(candidate.getGroupId()) : "N/A")
                .enroll(candidate.getEnrollStatus() != null ? candidate.getEnrollStatus() : "Not Enrolled")
                .status(candidate.getStatus())
                .paymentStatus(candidate.getPaymentStatus() != null ? candidate.getPaymentStatus() : "Unpaid")
                .regDate(candidate.getDateOfRegistration() != null ? candidate.getDateOfRegistration().toString() : "")
                .build();
    }
}
