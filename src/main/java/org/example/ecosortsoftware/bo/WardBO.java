package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.dto.WardDto;
import org.example.ecosortsoftware.entity.Ward;

import java.sql.SQLException;
import java.util.ArrayList;

public interface WardBO extends SuperBO{
    WardDto FindById(String selectedId) throws SQLException, ClassNotFoundException;

    ArrayList<WardDto> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllDevisionIds(String municipalId) throws SQLException, ClassNotFoundException;
}
