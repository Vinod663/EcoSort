package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.dto.InventoryDto;
import org.example.ecosortsoftware.entity.Inventory;

import java.sql.SQLException;

public interface InventoryBO extends SuperBO{
    Inventory getAll(String municipalId) throws SQLException, ClassNotFoundException;

    double getInventoryCapacity(String inventoryId) throws SQLException, ClassNotFoundException;

    boolean update(InventoryDto inventorydto) throws SQLException, ClassNotFoundException;
}
