package app.hospital.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CandidateRequest {

    // Login Detail
    @NotBlank(message = "Email is required")
    private String email;
    private String password;

    // Candidate Detail
    @NotBlank(message = "Candidate Name is required")
    private String candidateName;
    
    private String enrollmentNumber;
    
    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;
    
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private LocalDate dateOfRegistration;
    private String gender;
    private Boolean disability;

    // Upload Detail 
    private String profilePicture;
    private String signature;
    private String idProof;
    private String otherIdentification1;
    private String otherIdentification2;
    private String otherIdentification3;

    // Address Detail
    private String address;
    private String country;
    private String state;
    private String city;
    private String zipcode;

    // Extra Field Detail
    private String extraField1;
    private String extraField2;
    private String extraField3;
    private String extraField4;
    private String extraField5;
    private String extraField6;
    private String extraField7;
    private String documentUpload1;
    private String documentUpload2;

    // Groups Detail
    private Long groupId;

    // Options
    private Boolean sendEmail;

    private String status = "Active";
    private String paymentStatus = "Unpaid";
    private String enrollStatus = "Not Enrolled";
}
