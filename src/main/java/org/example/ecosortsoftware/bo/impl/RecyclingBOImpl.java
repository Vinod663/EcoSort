package org.example.ecosortsoftware.bo.impl;

import org.example.ecosortsoftware.DAO.DAOFactory;
import org.example.ecosortsoftware.DAO.custom.RecyclingDAO;
import org.example.ecosortsoftware.bo.RecyclingBO;
import org.example.ecosortsoftware.dto.RecyclingDto;
import org.example.ecosortsoftware.entity.Recycling;

import java.sql.SQLException;

public class RecyclingBOImpl implements RecyclingBO {
    RecyclingDAO recyclingDAO= (RecyclingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RECYCLING);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return recyclingDAO.getNextId();
    }

    @Override
    public boolean save(RecyclingDto recyclingdto) throws SQLException, ClassNotFoundException {
        return recyclingDAO.save(new Recycling(recyclingdto.getRecyclingId(),recyclingdto.getInventoryId(),recyclingdto.getQuantity(),
                recyclingdto.getDate(),recyclingdto.getMunicipalId(),recyclingdto.getCollectionId()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return recyclingDAO.delete(id);
    }

    @Override
    public boolean update(RecyclingDto recyclingdto) throws SQLException, ClassNotFoundException {
        return recyclingDAO.update(new Recycling(recyclingdto.getRecyclingId(),recyclingdto.getInventoryId(),recyclingdto.getQuantity(),
                recyclingdto.getDate(),recyclingdto.getMunicipalId(),recyclingdto.getCollectionId()));
    }
}
