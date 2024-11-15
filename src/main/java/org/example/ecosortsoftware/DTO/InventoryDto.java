package org.example.ecosortsoftware.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InventoryDto {
    private String inventoryId;
    private double wasteAmount;
    private String status;
    private String municipalId;
    private double capacity;
}
