package org.example.ecosortsoftware.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Inventory {
    private String inventoryId;
    private double wasteAmount;
    private String status;
    private String municipalId;
    private double capacity;
}
