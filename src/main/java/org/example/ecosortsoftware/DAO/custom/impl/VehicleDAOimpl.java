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
        return SQLUtil.execute("insert into vehicle values(?,?,?,?,?)",
                vehicle.getVehicleId(),vehicle.getEmployeeId(),vehicle.getLicensePlate(),vehicle.getVehicleType(),vehicle.getMunicipalId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM vehicle WHERE vehicle_id=?",id);
    }

    @Override
    public boolean update(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("Update vehicle SET employee_id=?, license_plate=?, type=?, muni_id=? WHERE vehicle_id=?",
                vehicle.getEmployeeId(),vehicle.getLicensePlate(),vehicle.getVehicleType(),vehicle.getMunicipalId(),vehicle.getVehicleId());
    }

    @Override
    public ArrayList<Vehicle> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select * from vehicle where muni_id=?", municipalId);
        ArrayList<Vehicle> vehTms = new ArrayList<>();

        while (resultSet.next()) {
            Vehicle vehTm = new Vehicle();

            vehTm.setVehicleId(resultSet.getString(1));
            vehTm.setEmployeeId(resultSet.getString(2));
            vehTm.setLicensePlate(resultSet.getString(3));
            vehTm.setVehicleType(resultSet.getString(4));
            vehTm.setMunicipalId(resultSet.getString(5));

            vehTms.add(vehTm);
        }
        return vehTms;
    }

    public static ArrayList<String> getAllVehicleIds(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.execute("select * from vehicle where muni_id=?", municipalId);
        ArrayList<String> vehIds = new ArrayList<>();

        while (result.next()) {
            vehIds.add(result.getString("vehicle_id"));
        }
        return vehIds;
    }




}
