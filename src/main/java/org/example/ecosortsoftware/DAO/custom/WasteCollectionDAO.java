package org.example.ecosortsoftware.DAO.custom;

import org.example.ecosortsoftware.DAO.CrudDAO;
import org.example.ecosortsoftware.dto.WasteCollectionDto;
import org.example.ecosortsoftware.entity.WasteCollection;
import org.example.ecosortsoftware.view.tdm.WasteCollectionTm;

import java.sql.SQLException;
import java.util.ArrayList;

public interface WasteCollectionDAO extends CrudDAO<WasteCollection> {
    boolean updateCollection(WasteCollection collectionDto) throws SQLException, ClassNotFoundException;

    ArrayList<WasteCollectionTm> getAll(String municipalId) throws SQLException, ClassNotFoundException;

    double getTotalWaste(String muniId) throws SQLException, ClassNotFoundException;
}
