package org.example.ecosortsoftware.bo.impl;

import org.example.ecosortsoftware.DAO.DAOFactory;
import org.example.ecosortsoftware.DAO.custom.MunicipalDAO;
import org.example.ecosortsoftware.DAO.custom.impl.MunicipalDAOimpl;
import org.example.ecosortsoftware.bo.MunicipalBO;
import org.example.ecosortsoftware.dto.MunicipalDto;
import org.example.ecosortsoftware.entity.Municipal;

import java.sql.SQLException;
import java.util.ArrayList;

public class MunicipalBOImpl implements MunicipalBO {
    MunicipalDAO municipalDAO= (MunicipalDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MUNICIPAL);

    @Override
    public ArrayList<MunicipalDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<Municipal> all = MunicipalDAOimpl.getAll();

        ArrayList<MunicipalDto> municipals = new ArrayList<>();
        for(Municipal municipal : all){
            municipals.add(new MunicipalDto(municipal.getMunicipalId(),municipal.getMunicipalName()));
        }
        return municipals;
    }
}
