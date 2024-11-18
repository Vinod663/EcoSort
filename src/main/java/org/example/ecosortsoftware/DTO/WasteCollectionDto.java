package org.example.ecosortsoftware.DTO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WasteCollectionDto {
    private String collectionId;
    private String vehicleId;
    private String inventoryId;
    private double totalWasteAmount;
    private String collectionDate;
    private String divisionId;
    private double collectedWasteAmount;
    private double degradableWasteAmount;
    private double recyclableWasteAmount;
    private double nonRecyclableWasteAmount;
    private String municipalId;



}
