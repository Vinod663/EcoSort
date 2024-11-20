package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.DTO.Tm.EmployeeTm;
import org.example.ecosortsoftware.DTO.Tm.WasteCollectionTm;
import org.example.ecosortsoftware.DTO.VehicleDto;
import org.example.ecosortsoftware.DTO.WasteCollectionDto;
import org.example.ecosortsoftware.Utill.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WasteCollectionModel {
    public static ArrayList<String> getAllVehicleIds(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtill.execute("select * from vehicle where muni_id=?", municipalId);
        ArrayList<String> vehIds = new ArrayList<>();

        while (result.next()) {
            vehIds.add(result.getString("vehicle_id"));
        }
        return vehIds;
    }

    public static ArrayList<String> getAllDevisionIds(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtill.execute("select * from division where municipal_id=?", municipalId);
        ArrayList<String> devIds = new ArrayList<>();

        while (result.next()) {
            devIds.add(result.getString("division_id"));
        }
        return devIds;
    }

    public static ArrayList<WasteCollectionDto> GetData(String munId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtill.execute("select DATE_FORMAT(collection_date, '%d-%b') AS date,recyclable_waste_amount,degradable_waste_amount,nonRecyclable_waste_amount from waste_collection where municilId=? order by collection_date", munId);
        ArrayList<WasteCollectionDto> collectionDto = new ArrayList<>();
        while (result.next()) {
            WasteCollectionDto dto = new WasteCollectionDto();


            dto.setCollectionDate(result.getString("date"));
            dto.setRecyclableWasteAmount(result.getDouble("recyclable_waste_amount"));
            dto.setDegradableWasteAmount(result.getDouble("degradable_waste_amount"));
            dto.setNonRecyclableWasteAmount(result.getDouble("nonRecyclable_waste_amount"));
            collectionDto.add(dto);
        }
        return collectionDto;
    }

    public String getNextCollectionId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select collection_id from waste_collection order by collection_id desc limit 1;");

        if (resultSet.next()) {
            String lastColId = resultSet.getString(1);
            String subString = lastColId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;

            return String.format("C0%03d", newIndex);
        }
        return "C0001";
    }

    public ArrayList<WasteCollectionTm> getAll(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select * from waste_collection where municilId=?", municipalId);
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

    public double getTotalWaste(String muniId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select total_waste from waste_collection where municilId=? order by total_waste desc limit 1",muniId);
        if (resultSet.next()) {
            return resultSet.getDouble("total_waste");
        }
        return 0;
    }
    public boolean saveCollection(WasteCollectionDto collectionDto) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("insert into waste_collection values(?,?,?,?,?,?,?,?,?,?,?)",
                collectionDto.getCollectionId(),collectionDto.getVehicleId(),collectionDto.getInventoryId(),collectionDto.getTotalWasteAmount(),collectionDto.getCollectionDate(),
                collectionDto.getDivisionId(),collectionDto.getCollectedWasteAmount(),collectionDto.getDegradableWasteAmount(),collectionDto.getRecyclableWasteAmount(),
                collectionDto.getNonRecyclableWasteAmount(),collectionDto.getMunicipalId());
    }

    public boolean updateCollection(WasteCollectionDto collectionDto) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("update waste_collection set municilId=?, vehicle_id=?, inventory_id=?, total_waste=?, collection_date=?, " +
                        "division_id=?, collected_waste_amount=?, degradable_waste_amount=?, recyclable_waste_amount=?, nonRecyclable_waste_amount=? where collection_id=?",
                collectionDto.getMunicipalId(),collectionDto.getVehicleId(),collectionDto.getInventoryId(),collectionDto.getTotalWasteAmount(),collectionDto.getCollectionDate(),
                collectionDto.getDivisionId(),collectionDto.getCollectedWasteAmount(),collectionDto.getDegradableWasteAmount(),collectionDto.getRecyclableWasteAmount(),
                collectionDto.getNonRecyclableWasteAmount(),collectionDto.getCollectionId());
    }

    public boolean DeleteCollection(String collectionId) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("DELETE FROM waste_collection WHERE collection_id=?",collectionId);
    }
}
