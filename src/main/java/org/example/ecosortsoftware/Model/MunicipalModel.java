package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.DTO.MunicipalDto;
import org.example.ecosortsoftware.DTO.Tm.MunicipalTm;
import org.example.ecosortsoftware.Utill.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MunicipalModel {
    public static ArrayList<MunicipalDto> getAll() throws SQLException, ClassNotFoundException {

        ResultSet result= CrudUtill.execute("select * from municipal;");
        ArrayList<MunicipalDto> municipalDtos = new ArrayList<>();
        while (result.next()) {
            MunicipalDto municipalDto = new MunicipalDto(
                    result.getString(1),
                    result.getString(2)
            );
            municipalDtos.add(municipalDto);

        }
        return municipalDtos;
    }
}
