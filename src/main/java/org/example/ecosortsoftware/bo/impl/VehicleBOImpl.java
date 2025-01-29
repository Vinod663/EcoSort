package org.example.ecosortsoftware.bo.impl;

import org.example.ecosortsoftware.DAO.DAOFactory;
import org.example.ecosortsoftware.DAO.custom.VehicleDAO;
import org.example.ecosortsoftware.bo.VehicleBO;
import org.example.ecosortsoftware.dto.VehicleDto;
import org.example.ecosortsoftware.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleBOImpl implements VehicleBO {
    VehicleDAO vehicleDAO= (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return vehicleDAO.getNextId();
    }

    @Override
    public Vehicle FindById(String selectedId) throws SQLException, ClassNotFoundException {
        return vehicleDAO.FindById(selectedId);
    }

    @Override
    public boolean save(VehicleDto vehicledto) throws SQLException, ClassNotFoundException {
        return vehicleDAO.save(new Vehicle(vehicledto.getVehicleId(),vehicledto.getEmployeeId(),vehicledto.getLicensePlate(),
                vehicledto.getVehicleType(),vehicledto.getMunicipalId()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return vehicleDAO.delete(id);
    }

    @Override
    public boolean update(VehicleDto vehicledto) throws SQLException, ClassNotFoundException {
        return vehicleDAO.update(new Vehicle(vehicledto.getVehicleId(),vehicledto.getEmployeeId(),vehicledto.getLicensePlate(),
                vehicledto.getVehicleType(),vehicledto.getMunicipalId()));
    }

    @Override
    public ArrayList<Vehicle> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        return vehicleDAO.getAllFromMunicipal(municipalId);
    }
}
