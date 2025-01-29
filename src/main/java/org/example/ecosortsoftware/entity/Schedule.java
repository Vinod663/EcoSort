package org.example.ecosortsoftware.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Schedule {
    private String municipalId;
    private String divisionId;
    private String depot;
    private String degradableWaste;
    private String recyclableWaste;
    private String nonRecyclableWaste;
}
