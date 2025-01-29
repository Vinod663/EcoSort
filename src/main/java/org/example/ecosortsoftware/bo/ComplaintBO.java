package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.dto.ComplaintsDto;
import org.example.ecosortsoftware.entity.Complaints;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ComplaintBO extends SuperBO{
    String getNextId() throws SQLException, ClassNotFoundException;

    Complaints FindById(String selectedId) throws SQLException, ClassNotFoundException;

    boolean save(ComplaintsDto complaintsdto) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    boolean update(ComplaintsDto complaintsdto) throws SQLException, ClassNotFoundException;

    ArrayList<Complaints> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException;
}
