package org.example.ecosortsoftware.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DisposalDto {
    private String disposalId;
    private Date disposalDate;
    private double wasteAmount;
    private String MunicipalId;
}
