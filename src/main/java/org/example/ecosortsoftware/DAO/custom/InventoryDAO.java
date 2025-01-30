package org.example.ecosortsoftware.DAO.custom;

import org.example.ecosortsoftware.DAO.CrudDAO;
import org.example.ecosortsoftware.entity.Inventory;

import java.sql.SQLException;

public interface InventoryDAO extends CrudDAO<Inventory> {

    Inventory getAll(String municipalId) throws SQLException, ClassNotFoundException;

    double getInventoryCapacity(String inventoryId) throws SQLException, ClassNotFoundException;
}
