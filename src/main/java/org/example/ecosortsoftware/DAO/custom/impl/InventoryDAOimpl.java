package org.example.ecosortsoftware.DAO.custom.impl;

import org.example.ecosortsoftware.DAO.SQLUtil;
import org.example.ecosortsoftware.DAO.custom.InventoryDAO;
import org.example.ecosortsoftware.entity.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAOimpl implements InventoryDAO {

    @Override
    public Inventory getAll(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select * from inventory where municipalId=?", municipalId);
        Inventory inventory = new Inventory();
        while (resultSet.next()) {

            inventory.setInventoryId(resultSet.getString(1));
            inventory.setWasteAmount(resultSet.getDouble(2));
            inventory.setStatus(resultSet.getString(3));
            inventory.setMunicipalId(resultSet.getString(4));
            inventory.setCapacity(resultSet.getDouble(5));
        }
        return inventory;
    }

    @Override
    public double getInventoryCapacity(String inventoryId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select capacity from inventory where inventory_id=? order by inventory_id desc limit 1",inventoryId);
        if (resultSet.next()) {
            return resultSet.getDouble("capacity");
        }
        return 0;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public Inventory FindById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Inventory dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Inventory inventory) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update inventory set inventory_id=?, waste_amount=?, status=?, capacity=? where municipalId=?",
                inventory.getInventoryId(),inventory.getWasteAmount(),
                inventory.getStatus(),inventory.getCapacity(),inventory.getMunicipalId());
    }

    @Override
    public ArrayList<Inventory> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        return null;
    }
}
