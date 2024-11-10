package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.DTO.Tm.SheduleTm;
import org.example.ecosortsoftware.DTO.Tm.WardTm;
import org.example.ecosortsoftware.Utill.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WardModel {
    public ArrayList<WardTm> getAll(String MunicipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select * from division where municipal_id=?", MunicipalId);
        ArrayList<WardTm> wardTms = new ArrayList<>();

        while (resultSet.next()) {
            WardTm wardTm = new WardTm();

            wardTm.setWardId(resultSet.getString("division_id"));
            wardTm.setMunicipalId(resultSet.getString("municipal_id"));
            wardTm.setWardName(resultSet.getString("name"));
            wardTms.add(wardTm);
        }
        return wardTms;
    }

    public ArrayList<String> getAllWardNames(String MunicipalId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtill.execute("select * from division where municipal_id=?", MunicipalId);
        ArrayList<String> wardNames = new ArrayList<>();

        while (result.next()) {
            wardNames.add(result.getString("name"));
        }
        return wardNames;
    }
}
