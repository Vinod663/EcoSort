package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.dto.VehicleDto;
import org.example.ecosortsoftware.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleBO extends SuperBO{
    String getNextId() throws SQLException, ClassNotFoundException;

    VehicleDto FindById(String selectedId) throws SQLException, ClassNotFoundException;

    boolean save(VehicleDto vehicledto) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    boolean update(VehicleDto vehicledto) throws SQLException, ClassNotFoundException;

    ArrayList<VehicleDto> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllVehicleIds(String municipalId) throws SQLException, ClassNotFoundException;
}
