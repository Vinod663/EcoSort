package org.example.ecosortsoftware.view.tdm;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WasteCollectionTm {
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
