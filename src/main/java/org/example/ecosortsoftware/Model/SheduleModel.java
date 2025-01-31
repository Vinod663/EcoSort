package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.dto.SheduleDto;
import org.example.ecosortsoftware.view.tdm.SheduleTm;
import org.example.ecosortsoftware.view.tdm.WardTm;
import org.example.ecosortsoftware.DAO.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SheduleModel {
    public boolean checkMunicipalData(String MunicipalId)  {
        System.out.println("Municipal ID:" + MunicipalId);
        try{
            ResultSet result = SQLUtil.execute("SELECT * FROM Waste_Collection_Shedule WHERE municipal_id=?", MunicipalId);



            if (result == null) {
                System.out.println("ResultSet is null, query execution failed.");
            } else if (!result.next()) {
                System.out.println("ResultSet is not empty but no matching rows found.");
            } else {
                System.out.println("Matching row found for Municipal ID: " + MunicipalId);
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Authentication failed
    }

    public ArrayList<SheduleTm> getAll(String MunicipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select * from waste_collection_shedule where municipal_id=?", MunicipalId);
        ArrayList<SheduleTm> sheduleTms = new ArrayList<>();

        while (resultSet.next()) {
            SheduleTm sheduleTm = new SheduleTm();

            sheduleTm.setMunicipalId(resultSet.getString("municipal_id"));
            sheduleTm.setDivisionId(resultSet.getString("division_id"));
            sheduleTm.setDepot(resultSet.getString("depot"));
            sheduleTm.setDegradableWaste(resultSet.getString("degradable_waste"));
            sheduleTm.setRecyclableWaste(resultSet.getString("recyclable_waste"));
            sheduleTm.setNonRecyclableWaste(resultSet.getString("non_recyclable_waste"));
            sheduleTms.add(sheduleTm);
        }
        return sheduleTms;
    }

    public boolean insertWards(ArrayList<WardTm> wardTms) throws SQLException, ClassNotFoundException {

        for (WardTm ward : wardTms) {
            boolean result = SQLUtil.execute(
                    "INSERT INTO Waste_Collection_Shedule (municipal_id, division_id, depot, degradable_waste, recyclable_waste, non_recyclable_waste) VALUES (?, ?, ?, ?, ?, ?)",
                    ward.getMunicipalId(),
                    ward.getWardId(),
                    ward.getWardName(),
                    null,    // degradable_waste
                    null,    // recyclable_waste
                    null     // non_recyclable_waste
            );

            // If any insert fails, return false
            if (!result) {
                return false;
            }
        }
        // All inserts succeeded
        return true;
    }

    public boolean updateShedule(SheduleDto sheduleDto) throws SQLException, ClassNotFoundException {

       return SQLUtil.execute("UPDATE waste_collection_shedule SET municipal_id=?, depot=?, degradable_waste=?, recyclable_waste=?,non_recyclable_waste=? WHERE division_id=? "
       ,sheduleDto.getMunicipalId()
       ,sheduleDto.getDepot()
       ,sheduleDto.getDegradableWaste()
       ,sheduleDto.getRecyclableWaste()
       ,sheduleDto.getNonRecyclableWaste()
       ,sheduleDto.getDivisionId());
    }

}
