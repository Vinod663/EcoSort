package org.example.ecosortsoftware.DTO.Tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SheduleTm {
    private String municipalId;
    private String divisionId;
    private String depot;
    private String degradableWaste;
    private String recyclableWaste;
    private String nonRecyclableWaste;
}
