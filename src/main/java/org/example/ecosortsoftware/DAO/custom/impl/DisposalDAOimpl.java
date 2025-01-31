package org.example.ecosortsoftware.DAO.custom.impl;

import org.example.ecosortsoftware.DAO.SQLUtil;
import org.example.ecosortsoftware.DAO.custom.DisposalDAO;
import org.example.ecosortsoftware.entity.Disposal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DisposalDAOimpl implements DisposalDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select disposal_id from disposal order by disposal_id desc limit 1;");

        if (resultSet.next()) {
            String lastColId = resultSet.getString(1);
            String subString = lastColId.substring(2);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;

            return String.format("DI0%03d", newIndex);
        }
        return "DI0001";
    }

    @Override
    public Disposal FindById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Disposal disposal) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into disposal values(?,?,?,?)",
                disposal.getDisposalId(),disposal.getDisposalDate(),disposal.getWasteAmount(),disposal.getMunicipalId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Disposal disposal) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<Disposal> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        return null;
    }
}
