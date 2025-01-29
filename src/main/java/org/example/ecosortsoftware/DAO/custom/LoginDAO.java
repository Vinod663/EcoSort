package org.example.ecosortsoftware.DAO.custom;

import org.example.ecosortsoftware.DAO.CrudDAO;

import java.sql.SQLException;

public interface LoginDAO extends CrudDAO {
    boolean authenticate(String enteredUsername, String enteredPassword) throws SQLException, ClassNotFoundException;
    boolean authenticateUsername(String enteredUsername) throws SQLException, ClassNotFoundException;
    boolean authenticatePsw(String enteredPassword) throws SQLException, ClassNotFoundException;
}
