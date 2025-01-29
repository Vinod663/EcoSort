package org.example.ecosortsoftware.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ComplaintsDto {
    private String complaintId;
    private String description;
    private String status;
    private String municipalId;
}
