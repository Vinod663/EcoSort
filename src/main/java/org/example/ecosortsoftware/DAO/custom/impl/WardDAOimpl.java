package org.example.ecosortsoftware.DAO.custom.impl;

import org.example.ecosortsoftware.DAO.SQLUtil;
import org.example.ecosortsoftware.DAO.custom.WardDAO;
import org.example.ecosortsoftware.dto.WardDto;
import org.example.ecosortsoftware.entity.Ward;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WardDAOimpl implements WardDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public Ward FindById(String selectedId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from division where division_id=?", selectedId);
        if (rst.next()) {
            return new Ward(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }
        return null;
    }

    @Override
    public boolean save(Ward dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Ward dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<Ward> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select * from division where municipal_id=?", municipalId);
        ArrayList<Ward> wards = new ArrayList<>();

        while (resultSet.next()) {
            Ward ward = new Ward();

            ward.setWardId(resultSet.getString("division_id"));
            ward.setMunicipalId(resultSet.getString("municipal_id"));
            ward.setWardName(resultSet.getString("name"));
            wards.add(ward);
        }
        return wards;
    }

    public static ArrayList<String> getAllDevisionIds(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.execute("select * from division where municipal_id=?", municipalId);
        ArrayList<String> devIds = new ArrayList<>();

        while (result.next()) {
            devIds.add(result.getString("division_id"));
        }
        return devIds;
    }
}
