package org.example.ecosortsoftware.bo.impl;

import org.example.ecosortsoftware.DAO.DAOFactory;
import org.example.ecosortsoftware.DAO.custom.ScheduleDAO;
import org.example.ecosortsoftware.bo.ScheduleBO;
import org.example.ecosortsoftware.dto.ScheduleDto;
import org.example.ecosortsoftware.dto.WardDto;
import org.example.ecosortsoftware.entity.Schedule;
import org.example.ecosortsoftware.entity.Ward;

import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleBOImpl implements ScheduleBO {
    ScheduleDAO scheduleDAO= (ScheduleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SCHEDULE);

    @Override
    public boolean update(ScheduleDto schedule) throws SQLException, ClassNotFoundException {
        return scheduleDAO.update(new Schedule(
                 schedule.getMunicipalId()
                ,schedule.getDivisionId()
                ,schedule.getDepot()
                ,schedule.getDegradableWaste()
                ,schedule.getRecyclableWaste()
                ,schedule.getNonRecyclableWaste()
                ));
    }

    @Override
    public ArrayList<ScheduleDto> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        ArrayList<Schedule> allFromMunicipal = scheduleDAO.getAllFromMunicipal(municipalId);
        ArrayList<ScheduleDto> allFromMunicipalDto = new ArrayList<>();

        for (Schedule schedule : allFromMunicipal) {
            allFromMunicipalDto.add(new ScheduleDto(schedule.getMunicipalId(),schedule.getDivisionId(),
                    schedule.getDepot(),schedule.getDegradableWaste(),schedule.getRecyclableWaste(),schedule.getNonRecyclableWaste()));
        }
        return allFromMunicipalDto;
    }

    @Override
    public boolean checkMunicipalData(String MunicipalId)  {
        return scheduleDAO.checkMunicipalData(MunicipalId);
    }

    @Override
    public boolean insertWards(ArrayList<WardDto> wards) throws SQLException, ClassNotFoundException {

        ArrayList<Ward> wardArrayList = new ArrayList<>();
        for (WardDto ward : wards) {
            wardArrayList.add(new Ward(ward.getWardId(),ward.getMunicipalId(),ward.getWardName()));
        }
        return scheduleDAO.insertWards(wardArrayList);
    }

}
