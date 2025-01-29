package org.example.ecosortsoftware.DAO.custom.impl;

import org.example.ecosortsoftware.DAO.SQLUtil;
import org.example.ecosortsoftware.DAO.custom.VehicleDAO;
import org.example.ecosortsoftware.entity.Vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDAOimpl implements VehicleDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select vehicle_id from vehicle order by vehicle_id desc limit 1;");

        if (resultSet.next()) {
            String lastVeId = resultSet.getString(1);
            String subString = lastVeId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;

            return String.format("V0%03d", newIndex);
        }
        return "V0001";
    }

    @Override
    public Vehicle FindById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        return false;
    }

    
}
