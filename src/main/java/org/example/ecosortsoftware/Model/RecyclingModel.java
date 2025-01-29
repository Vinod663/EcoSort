package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.dto.RecyclingDto;
import org.example.ecosortsoftware.DAO.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecyclingModel {
    public String getNextRecyclingId() throws SQLException, ClassNotFoundException {
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

    public boolean saveRecycling(RecyclingDto recyclingDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into recycling values(?,?,?,?,?,?)",
                recyclingDto.getRecyclingId(),recyclingDto.getInventoryId(),recyclingDto.getQuantity(),recyclingDto.getDate(),recyclingDto.getMunicipalId(),recyclingDto.getCollectionId());
    }


    public boolean updateRecycling(RecyclingDto recyclingDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update recycling set recycling_id=?, inventory_id=?, quantity=?, recycling_date=?, municiId=? where collectionId=?",
                recyclingDto.getRecyclingId(),recyclingDto.getInventoryId(),recyclingDto.getQuantity(),recyclingDto.getDate(),recyclingDto.getMunicipalId(),recyclingDto.getCollectionId());
    }

    public boolean DeleteRecycling(String collectionId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM recycling WHERE collectionId=?",collectionId);
    }
}
