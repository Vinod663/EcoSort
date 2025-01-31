package org.example.ecosortsoftware.DAO.custom.impl;

import org.example.ecosortsoftware.DAO.SQLUtil;
import org.example.ecosortsoftware.DAO.custom.RecyclingDAO;
import org.example.ecosortsoftware.entity.Recycling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecyclingDAOimpl implements RecyclingDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select recycling_id from recycling order by recycling_id desc limit 1;");

        if (resultSet.next()) {
            String lastColId = resultSet.getString(1);
            String subString = lastColId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;

            return String.format("R0%03d", newIndex);
        }
        return "R0001";
    }

    @Override
    public Recycling FindById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Recycling recycling) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into recycling values(?,?,?,?,?,?)",
                recycling.getRecyclingId(),recycling.getInventoryId(),recycling.getQuantity(),recycling.getDate(),
                recycling.getMunicipalId(),recycling.getCollectionId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM recycling WHERE collectionId=?",id);
    }

    @Override
    public boolean update(Recycling recycling) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update recycling set recycling_id=?, inventory_id=?, quantity=?, recycling_date=?, municiId=? where collectionId=?",
                recycling.getRecyclingId(),recycling.getInventoryId(),recycling.getQuantity(),recycling.getDate(),
                recycling.getMunicipalId(),recycling.getCollectionId());
    }

    @Override
    public ArrayList<Recycling> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        return null;
    }
}
