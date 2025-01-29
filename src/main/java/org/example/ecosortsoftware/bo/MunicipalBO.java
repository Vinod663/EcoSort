package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.dto.MunicipalDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MunicipalBO extends SuperBO{
    ArrayList<MunicipalDto> getAll() throws SQLException, ClassNotFoundException;
}
