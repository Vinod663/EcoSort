package org.example.ecosortsoftware.dto.Tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ComplaintsTm {
    private String complaintId;
    private String description;
    private String status;
    private String municipalId;
}
