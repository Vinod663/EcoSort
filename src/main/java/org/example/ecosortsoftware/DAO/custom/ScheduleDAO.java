package org.example.ecosortsoftware.DAO.custom;

import org.example.ecosortsoftware.DAO.CrudDAO;
import org.example.ecosortsoftware.entity.Schedule;
import org.example.ecosortsoftware.entity.Ward;
import org.example.ecosortsoftware.view.tdm.WardTm;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ScheduleDAO extends CrudDAO<Schedule> {
    boolean checkMunicipalData(String MunicipalId);

    boolean insertWards(ArrayList<Ward> wards) throws SQLException, ClassNotFoundException;
}
