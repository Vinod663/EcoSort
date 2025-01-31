package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.dto.RecyclingDto;

import java.sql.SQLException;

public interface RecyclingBO extends SuperBO{
    String getNextId() throws SQLException, ClassNotFoundException;

    boolean save(RecyclingDto recyclingdto) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    boolean update(RecyclingDto recyclingdto) throws SQLException, ClassNotFoundException;
}
