package org.example.ecosortsoftware.bo.impl;

import org.example.ecosortsoftware.DAO.DAOFactory;
import org.example.ecosortsoftware.DAO.custom.WardDAO;
import org.example.ecosortsoftware.bo.WardBO;
import org.example.ecosortsoftware.dto.WardDto;
import org.example.ecosortsoftware.entity.Ward;

import java.sql.SQLException;
import java.util.ArrayList;

public class WardBOImpl implements WardBO {
    WardDAO wardDAO= (WardDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.WARD);

    @Override
    public WardDto FindById(String selectedId) throws SQLException, ClassNotFoundException {
        Ward ward = wardDAO.FindById(selectedId);
        return new WardDto(ward.getWardId(),ward.getMunicipalId(),ward.getWardName());
    }

    @Override
    public ArrayList<WardDto> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        ArrayList<Ward> allFromMunicipal = wardDAO.getAllFromMunicipal(municipalId);

        ArrayList<WardDto> wards = new ArrayList<>();
        for(Ward ward : allFromMunicipal){
            wards.add(new WardDto(ward.getWardId(),ward.getMunicipalId(),ward.getWardName()));
        }
        return wards;
    }
}
