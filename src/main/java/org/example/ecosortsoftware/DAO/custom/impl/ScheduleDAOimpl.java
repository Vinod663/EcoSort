package org.example.ecosortsoftware.DAO.custom.impl;

import org.example.ecosortsoftware.DAO.SQLUtil;
import org.example.ecosortsoftware.DAO.custom.ScheduleDAO;
import org.example.ecosortsoftware.entity.Schedule;
import org.example.ecosortsoftware.entity.Ward;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleDAOimpl implements ScheduleDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public Schedule FindById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Schedule dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Schedule schedule) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE waste_collection_shedule SET municipal_id=?, depot=?, degradable_waste=?, recyclable_waste=?,non_recyclable_waste=? WHERE division_id=? "
                ,schedule.getMunicipalId()
                ,schedule.getDepot()
                ,schedule.getDegradableWaste()
                ,schedule.getRecyclableWaste()
                ,schedule.getNonRecyclableWaste()
                ,schedule.getDivisionId());
    }

    @Override
    public ArrayList<Schedule> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select * from waste_collection_shedule where municipal_id=?", municipalId);
        ArrayList<Schedule> scheduleTms = new ArrayList<>();

        while (resultSet.next()) {
            Schedule schedule = new Schedule();

            schedule.setMunicipalId(resultSet.getString("municipal_id"));
            schedule.setDivisionId(resultSet.getString("division_id"));
            schedule.setDepot(resultSet.getString("depot"));
            schedule.setDegradableWaste(resultSet.getString("degradable_waste"));
            schedule.setRecyclableWaste(resultSet.getString("recyclable_waste"));
            schedule.setNonRecyclableWaste(resultSet.getString("non_recyclable_waste"));
            scheduleTms.add(schedule);
        }
        return scheduleTms;
    }

    @Override
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

    @Override
    public boolean insertWards(ArrayList<Ward> wards) throws SQLException, ClassNotFoundException {

        for (Ward ward : wards) {
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
}
