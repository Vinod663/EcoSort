package org.example.ecosortsoftware.bo.impl;

import org.example.ecosortsoftware.DAO.DAOFactory;
import org.example.ecosortsoftware.DAO.custom.VehicleDAO;
import org.example.ecosortsoftware.DAO.custom.impl.VehicleDAOimpl;
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
    public VehicleDto FindById(String selectedId) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = vehicleDAO.FindById(selectedId);
        return new VehicleDto(vehicle.getVehicleId(),vehicle.getEmployeeId(),vehicle.getLicensePlate(),
                vehicle.getVehicleType(),vehicle.getMunicipalId());
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
    public ArrayList<VehicleDto> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        ArrayList<Vehicle> allFromMunicipal = vehicleDAO.getAllFromMunicipal(municipalId);

        ArrayList<VehicleDto> vehicles = new ArrayList<>();

        for(Vehicle vehicle : allFromMunicipal){
            vehicles.add(new VehicleDto(vehicle.getVehicleId(),vehicle.getEmployeeId(),vehicle.getLicensePlate(),
                    vehicle.getVehicleType(),vehicle.getMunicipalId()));
        }
        return vehicles;
    }

    @Override
    public ArrayList<String> getAllVehicleIds(String municipalId) throws SQLException, ClassNotFoundException {
        return VehicleDAOimpl.getAllVehicleIds(municipalId);
    }
}
