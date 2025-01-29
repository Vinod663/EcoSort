package org.example.ecosortsoftware.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecyclingDto {
    private String recyclingId;
    private String inventoryId;
    private double quantity;
    private String date;
    private String municipalId;
    private String collectionId;
}
