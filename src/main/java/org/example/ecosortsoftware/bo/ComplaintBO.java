package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.dto.ComplaintsDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ComplaintBO extends SuperBO{
    String getNextId() throws SQLException, ClassNotFoundException;

    ComplaintsDto FindById(String selectedId) throws SQLException, ClassNotFoundException;

    boolean save(ComplaintsDto complaintsdto) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    boolean update(ComplaintsDto complaintsdto) throws SQLException, ClassNotFoundException;

    ArrayList<ComplaintsDto> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException;
}
