package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.dto.DisposalDto;

import java.sql.SQLException;

public interface DisposalBO extends SuperBO{
    String getNextId() throws SQLException, ClassNotFoundException;

    boolean save(DisposalDto disposaldto) throws SQLException, ClassNotFoundException;
}
