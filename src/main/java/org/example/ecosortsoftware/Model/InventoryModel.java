package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.DTO.InventoryDto;
import org.example.ecosortsoftware.DTO.Tm.VehicleTm;
import org.example.ecosortsoftware.Utill.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryModel {
    public InventoryDto getAll(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select * from inventory where municipalId=?", municipalId);
        InventoryDto inventoryDto = new InventoryDto();
        while (resultSet.next()) {

           inventoryDto.setInventoryId(resultSet.getString(1));
           inventoryDto.setWasteAmount(resultSet.getDouble(2));
           inventoryDto.setStatus(resultSet.getString(3));
           inventoryDto.setMunicipalId(resultSet.getString(4));
           inventoryDto.setCapacity(resultSet.getDouble(5));
        }
        return inventoryDto;
    }

    public boolean updateInventory(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("update inventory set inventory_id=?, waste_amount=?, status=?, capacity=? where municipalId=?",
                inventoryDto.getInventoryId(),inventoryDto.getWasteAmount(),inventoryDto.getStatus(),inventoryDto.getCapacity(),inventoryDto.getMunicipalId());
    }

    public double getInventoryCapacity(String inventoryId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select capacity from inventory where inventory_id=? order by inventory_id desc limit 1",inventoryId);
        if (resultSet.next()) {
            return resultSet.getDouble("capacity");
        }
        return 0;
    }

}
