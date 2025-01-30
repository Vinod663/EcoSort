package org.example.ecosortsoftware.bo.impl;

import org.example.ecosortsoftware.DAO.DAOFactory;
import org.example.ecosortsoftware.DAO.custom.InventoryDAO;
import org.example.ecosortsoftware.bo.InventoryBO;
import org.example.ecosortsoftware.dto.InventoryDto;
import org.example.ecosortsoftware.entity.Inventory;

import java.sql.SQLException;

public class InventoryBOImpl implements InventoryBO {
    InventoryDAO inventoryDAO= (InventoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INVENTORY);

    @Override
    public Inventory getAll(String municipalId) throws SQLException, ClassNotFoundException {
        return inventoryDAO.getAll(municipalId);
    }

    @Override
    public double getInventoryCapacity(String inventoryId) throws SQLException, ClassNotFoundException {
        return inventoryDAO.getInventoryCapacity(inventoryId);
    }

    @Override
    public boolean update(InventoryDto inventorydto) throws SQLException, ClassNotFoundException {
        return inventoryDAO.update(new Inventory(inventorydto.getInventoryId(),inventorydto.getWasteAmount(),
                inventorydto.getStatus(),inventorydto.getMunicipalId(),inventorydto.getCapacity()));
    }
}
