package org.example.ecosortsoftware.DAO.custom.impl;

import org.example.ecosortsoftware.DAO.SQLUtil;
import org.example.ecosortsoftware.DAO.custom.WasteCollectionDAO;
import org.example.ecosortsoftware.dto.WasteCollectionDto;
import org.example.ecosortsoftware.entity.WasteCollection;
import org.example.ecosortsoftware.view.tdm.WasteCollectionTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WasteCollectionDAOimpl implements WasteCollectionDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select collection_id from waste_collection order by collection_id desc limit 1;");

        if (resultSet.next()) {
            String lastColId = resultSet.getString(1);
            String subString = lastColId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;

            return String.format("C0%03d", newIndex);
        }
        return "C0001";
    }

    @Override
    public WasteCollection FindById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(WasteCollection collectionDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into waste_collection values(?,?,?,?,?,?,?,?,?,?,?)",
                collectionDto.getCollectionId(),collectionDto.getVehicleId(),collectionDto.getInventoryId(),collectionDto.getTotalWasteAmount(),collectionDto.getCollectionDate(),
                collectionDto.getDivisionId(),collectionDto.getCollectedWasteAmount(),collectionDto.getDegradableWasteAmount(),collectionDto.getRecyclableWasteAmount(),
                collectionDto.getNonRecyclableWasteAmount(),collectionDto.getMunicipalId());

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM waste_collection WHERE collection_id=?",id);
    }

    @Override
    public boolean updateCollection(WasteCollection collectionDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update waste_collection set municilId=?, vehicle_id=?, inventory_id=?, total_waste=?, collection_date=?, " +
                        "division_id=?, collected_waste_amount=?, degradable_waste_amount=?, recyclable_waste_amount=?, nonRecyclable_waste_amount=? where collection_id=?",
                collectionDto.getMunicipalId(),collectionDto.getVehicleId(),collectionDto.getInventoryId(),collectionDto.getTotalWasteAmount(),collectionDto.getCollectionDate(),
                collectionDto.getDivisionId(),collectionDto.getCollectedWasteAmount(),collectionDto.getDegradableWasteAmount(),collectionDto.getRecyclableWasteAmount(),
                collectionDto.getNonRecyclableWasteAmount(),collectionDto.getCollectionId());
    }

    @Override
    public boolean update(WasteCollection collectionDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update waste_collection set municilId=?, vehicle_id=?, inventory_id=?, total_waste=?, collection_date=?, " +
                        "division_id=?, collected_waste_amount=?, degradable_waste_amount=?, recyclable_waste_amount=?, nonRecyclable_waste_amount=? where collection_id=?",
                collectionDto.getMunicipalId(),collectionDto.getVehicleId(),collectionDto.getInventoryId(),collectionDto.getTotalWasteAmount(),collectionDto.getCollectionDate(),
                collectionDto.getDivisionId(),collectionDto.getCollectedWasteAmount(),collectionDto.getDegradableWasteAmount(),collectionDto.getRecyclableWasteAmount(),
                collectionDto.getNonRecyclableWasteAmount(),collectionDto.getCollectionId());
    }

    @Override
    public ArrayList<WasteCollection> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        return null;
    }

    public static ArrayList<WasteCollection> GetData(String munId) throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.execute("select DATE_FORMAT(collection_date, '%d-%b') AS date, collection_id, recyclable_waste_amount,degradable_waste_amount,nonRecyclable_waste_amount from waste_collection where municilId=? order by collection_date", munId);
        ArrayList<WasteCollection> collectionDto = new ArrayList<>();
        while (result.next()) {
            WasteCollection waste = new WasteCollection();


            waste.setCollectionDate(result.getString("date"));
            waste.setRecyclableWasteAmount(result.getDouble("recyclable_waste_amount"));
            waste.setDegradableWasteAmount(result.getDouble("degradable_waste_amount"));
            waste.setNonRecyclableWasteAmount(result.getDouble("nonRecyclable_waste_amount"));
            waste.setCollectionId(result.getString("collection_id"));////////////////////
            collectionDto.add(waste);
        }
        return collectionDto;
    }

    @Override
    public ArrayList<WasteCollectionTm> getAll(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select * from waste_collection where municilId=?", municipalId);
        ArrayList<WasteCollectionTm> collectionTms = new ArrayList<>();

        while (resultSet.next()) {
            WasteCollectionTm wasteCollectionTm=new WasteCollectionTm();

            wasteCollectionTm.setCollectionId(resultSet.getString("collection_id"));
            wasteCollectionTm.setVehicleId(resultSet.getString("vehicle_id"));
            wasteCollectionTm.setInventoryId(resultSet.getString("inventory_id"));
            wasteCollectionTm.setTotalWasteAmount(resultSet.getDouble("total_waste"));
            wasteCollectionTm.setCollectionDate(resultSet.getString("collection_date"));
            wasteCollectionTm.setDivisionId(resultSet.getString("division_id"));
            wasteCollectionTm.setCollectedWasteAmount(resultSet.getDouble("collected_waste_amount"));
            wasteCollectionTm.setDegradableWasteAmount(resultSet.getDouble("degradable_waste_amount"));
            wasteCollectionTm.setRecyclableWasteAmount(resultSet.getDouble("recyclable_waste_amount"));
            wasteCollectionTm.setNonRecyclableWasteAmount(resultSet.getDouble("nonRecyclable_waste_amount"));
            wasteCollectionTm.setMunicipalId(resultSet.getString("municilId"));


            collectionTms.add(wasteCollectionTm);
        }
        return collectionTms;
    }

    public static ArrayList<WasteCollectionDto> getData(String munId) throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.execute("select DATE_FORMAT(collection_date, '%d-%b') AS date, collection_id, recyclable_waste_amount,degradable_waste_amount,nonRecyclable_waste_amount from waste_collection where municilId=? order by collection_date", munId);
        ArrayList<WasteCollectionDto> collectionDto = new ArrayList<>();
        while (result.next()) {
            WasteCollectionDto dto = new WasteCollectionDto();


            dto.setCollectionDate(result.getString("date"));
            dto.setRecyclableWasteAmount(result.getDouble("recyclable_waste_amount"));
            dto.setDegradableWasteAmount(result.getDouble("degradable_waste_amount"));
            dto.setNonRecyclableWasteAmount(result.getDouble("nonRecyclable_waste_amount"));
            dto.setCollectionId(result.getString("collection_id"));////////////////////
            collectionDto.add(dto);
        }
        return collectionDto;
    }

    @Override
    public double getTotalWaste(String muniId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select total_waste from waste_collection where municilId=? order by total_waste desc limit 1",muniId);
        if (resultSet.next()) {
            return resultSet.getDouble("total_waste");
        }
        return 0;
    }
}
