package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.dto.*;
import org.example.ecosortsoftware.entity.Inventory;
import org.example.ecosortsoftware.view.tdm.WasteCollectionTm;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceWasteDataBO extends SuperBO{
    String getNextId() throws SQLException, ClassNotFoundException;

    boolean save(WasteCollectionDto wasteDto) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    boolean update(WasteCollectionDto wasteDto) throws SQLException, ClassNotFoundException;

    double getTotalWaste(String muniId) throws SQLException, ClassNotFoundException;

    ArrayList<WasteCollectionTm> getAllWasteData(String municipalId) throws SQLException, ClassNotFoundException;

    double getInventoryCapacity(String inventoryId) throws SQLException, ClassNotFoundException;

    InventoryDto getAll(String municipalId) throws SQLException, ClassNotFoundException;

    boolean update(InventoryDto inventory) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllDevisionIds(String municipalId) throws SQLException, ClassNotFoundException;

    WardDto FindById(String selectedId) throws SQLException, ClassNotFoundException;

    String getNextRecycleId() throws SQLException, ClassNotFoundException;

    boolean save(RecyclingDto recyclingdto) throws SQLException, ClassNotFoundException;

    boolean update(RecyclingDto recyclingdto) throws SQLException, ClassNotFoundException;

    boolean updateCollection(WasteCollectionDto collectionDto) throws SQLException, ClassNotFoundException;

    ArrayList<VehicleDto> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllVehicleIds(String municipalId) throws SQLException, ClassNotFoundException;

    ArrayList<WasteCollectionDto> getData(String munId) throws SQLException, ClassNotFoundException;
}
