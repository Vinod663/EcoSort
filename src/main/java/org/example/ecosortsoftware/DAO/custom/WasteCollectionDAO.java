package org.example.ecosortsoftware.DAO.custom;

import org.example.ecosortsoftware.DAO.CrudDAO;
import org.example.ecosortsoftware.entity.WasteCollection;


import java.sql.SQLException;
import java.util.ArrayList;

public interface WasteCollectionDAO extends CrudDAO<WasteCollection> {
    boolean updateCollection(WasteCollection collectionDto) throws SQLException, ClassNotFoundException;

    ArrayList<WasteCollection> getAll(String municipalId) throws SQLException, ClassNotFoundException;

    double getTotalWaste(String muniId) throws SQLException, ClassNotFoundException;
}
