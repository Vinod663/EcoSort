package org.example.ecosortsoftware.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Recycling {
    private String recyclingId;
    private String inventoryId;
    private double quantity;
    private String date;
    private String municipalId;
    private String collectionId;
}
