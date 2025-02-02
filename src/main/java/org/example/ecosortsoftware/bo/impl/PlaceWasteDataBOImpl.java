package org.example.ecosortsoftware.bo.impl;

import org.example.ecosortsoftware.DAO.DAOFactory;
import org.example.ecosortsoftware.DAO.custom.*;
import org.example.ecosortsoftware.DAO.custom.impl.VehicleDAOimpl;
import org.example.ecosortsoftware.DAO.custom.impl.WardDAOimpl;
import org.example.ecosortsoftware.DAO.custom.impl.WasteCollectionDAOimpl;
import org.example.ecosortsoftware.bo.PlaceWasteDataBO;
import org.example.ecosortsoftware.dto.*;
import org.example.ecosortsoftware.entity.*;
import org.example.ecosortsoftware.view.tdm.WasteCollectionTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceWasteDataBOImpl implements PlaceWasteDataBO {
    InventoryDAO inventoryDAO= (InventoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INVENTORY);
    WardDAO wardDAO= (WardDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.WARD);
    RecyclingDAO recyclingDAO= (RecyclingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RECYCLING);
    VehicleDAO vehicleDAO= (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE);
    WasteCollectionDAO wasteCollectionDAO= (WasteCollectionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.WASTE);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return wasteCollectionDAO.getNextId();
    }

    @Override
    public boolean save(WasteCollectionDto collectionDto) throws SQLException, ClassNotFoundException {
        return wasteCollectionDAO.save(new WasteCollection(collectionDto.getCollectionId(),collectionDto.getVehicleId(),collectionDto.getInventoryId(),collectionDto.getTotalWasteAmount(),collectionDto.getCollectionDate(),
                collectionDto.getDivisionId(),collectionDto.getCollectedWasteAmount(),collectionDto.getDegradableWasteAmount(),collectionDto.getRecyclableWasteAmount(),
                collectionDto.getNonRecyclableWasteAmount(),collectionDto.getMunicipalId()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return wasteCollectionDAO.delete(id);
    }

    @Override
    public boolean update(WasteCollectionDto collectionDto) throws SQLException, ClassNotFoundException {
        return wasteCollectionDAO.update(new WasteCollection(collectionDto.getMunicipalId(),collectionDto.getVehicleId(),collectionDto.getInventoryId(),collectionDto.getTotalWasteAmount(),collectionDto.getCollectionDate(),
                collectionDto.getDivisionId(),collectionDto.getCollectedWasteAmount(),collectionDto.getDegradableWasteAmount(),collectionDto.getRecyclableWasteAmount(),
                collectionDto.getNonRecyclableWasteAmount(),collectionDto.getCollectionId()));
    }

    @Override
    public double getTotalWaste(String muniId) throws SQLException, ClassNotFoundException {
        return wasteCollectionDAO.getTotalWaste(muniId);
    }

    @Override
    public ArrayList<WasteCollectionTm> getAllWasteData(String municipalId) throws SQLException, ClassNotFoundException {
        return wasteCollectionDAO.getAll(municipalId);
    }

    @Override
    public double getInventoryCapacity(String inventoryId) throws SQLException, ClassNotFoundException {
        return inventoryDAO.getInventoryCapacity(inventoryId);
    }

    @Override
    public InventoryDto getAll(String municipalId) throws SQLException, ClassNotFoundException {
        Inventory all = inventoryDAO.getAll(municipalId);
        return new InventoryDto(all.getInventoryId(),all.getWasteAmount(),all.getStatus(),all.getMunicipalId(),all.getCapacity());
    }

    @Override
    public boolean update(InventoryDto inventory) throws SQLException, ClassNotFoundException {
        return inventoryDAO.update(new Inventory(inventory.getInventoryId(),inventory.getWasteAmount(),
                inventory.getStatus(),inventory.getMunicipalId(),inventory.getCapacity()));
    }

    @Override
    public ArrayList<String> getAllDevisionIds(String municipalId) throws SQLException, ClassNotFoundException {
        return WardDAOimpl.getAllDevisionIds(municipalId);
    }

    @Override
    public WardDto FindById(String selectedId) throws SQLException, ClassNotFoundException {
        Ward ward = wardDAO.FindById(selectedId);
        if (ward == null) {
            return null; // Return null to handle it in the controller
        }

        return new WardDto(ward.getWardId(), ward.getMunicipalId(), ward.getWardName());
    }

    @Override
    public String getNextRecycleId() throws SQLException, ClassNotFoundException {
        return recyclingDAO.getNextId();
    }

    @Override
    public boolean save(RecyclingDto recyclingdto) throws SQLException, ClassNotFoundException {
        return recyclingDAO.save(new Recycling(recyclingdto.getRecyclingId(),recyclingdto.getInventoryId(),recyclingdto.getQuantity(),
                recyclingdto.getDate(),recyclingdto.getMunicipalId(),recyclingdto.getCollectionId()));
    }

    @Override
    public boolean update(RecyclingDto recycling) throws SQLException, ClassNotFoundException {
        return recyclingDAO.update(new Recycling(recycling.getRecyclingId(),recycling.getInventoryId(),recycling.getQuantity(),recycling.getDate(),
                recycling.getMunicipalId(),recycling.getCollectionId()));
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

    @Override
    public boolean updateCollection(WasteCollectionDto collectionDto) throws SQLException, ClassNotFoundException {
        return wasteCollectionDAO.updateCollection(new WasteCollection(collectionDto.getCollectionId(),collectionDto.getVehicleId(),
                collectionDto.getInventoryId(),collectionDto.getTotalWasteAmount(),collectionDto.getCollectionDate(),collectionDto.getDivisionId(),
                collectionDto.getCollectedWasteAmount(),collectionDto.getDegradableWasteAmount(),collectionDto.getRecyclableWasteAmount(),
                collectionDto.getNonRecyclableWasteAmount(),collectionDto.getMunicipalId()));
    }

    @Override
    public ArrayList<WasteCollectionDto> getData(String munId) throws SQLException, ClassNotFoundException {
        return WasteCollectionDAOimpl.getData(munId);
    }

}
