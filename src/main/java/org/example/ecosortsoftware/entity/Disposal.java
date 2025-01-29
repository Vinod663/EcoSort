package org.example.ecosortsoftware.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Disposal {
    private String disposalId;
    private Date disposalDate;
    private double wasteAmount;
    private String MunicipalId;
}
