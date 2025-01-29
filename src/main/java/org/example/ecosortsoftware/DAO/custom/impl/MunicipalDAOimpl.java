package org.example.ecosortsoftware.DAO.custom.impl;

import org.example.ecosortsoftware.DAO.SQLUtil;
import org.example.ecosortsoftware.DAO.custom.MunicipalDAO;
import org.example.ecosortsoftware.dto.MunicipalDto;
import org.example.ecosortsoftware.entity.Municipal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MunicipalDAOimpl implements MunicipalDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public Municipal FindById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Municipal municipal) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Municipal municipal) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<Municipal> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        return null;
    }


    public static ArrayList<Municipal> getAll() throws SQLException, ClassNotFoundException {

        ResultSet result= SQLUtil.execute("select * from municipal;");
        ArrayList<Municipal> municipalDtos = new ArrayList<>();
        while (result.next()) {
            Municipal municipal = new Municipal(
                    result.getString(1),
                    result.getString(2)
            );
            municipalDtos.add(municipal);

        }
        return municipalDtos;
    }
}
