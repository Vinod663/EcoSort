package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.dto.ScheduleDto;
import org.example.ecosortsoftware.dto.WardDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ScheduleBO extends SuperBO{
    boolean update(ScheduleDto schedule) throws SQLException, ClassNotFoundException;

    ArrayList<ScheduleDto> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException;

    boolean checkMunicipalData(String MunicipalId);

    boolean insertWards(ArrayList<WardDto> wards) throws SQLException, ClassNotFoundException;
}
