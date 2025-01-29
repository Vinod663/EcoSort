package org.example.ecosortsoftware.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Complaints {
    private String complaintId;
    private String description;
    private String status;
    private String municipalId;
}
