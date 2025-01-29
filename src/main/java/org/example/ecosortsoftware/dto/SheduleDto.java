package org.example.ecosortsoftware.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SheduleDto {
    private String municipalId;
    private String divisionId;
    private String depot;
    private String degradableWaste;
    private String recyclableWaste;
    private String nonRecyclableWaste;
}
