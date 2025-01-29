package org.example.ecosortsoftware.bo;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {

    public boolean authenticate(String enteredUsername, String enteredPassword) throws SQLException, ClassNotFoundException;

    boolean authenticateUsername(String enteredUsername) throws SQLException, ClassNotFoundException;

    boolean authenticatePsw(String enteredPassword) throws SQLException, ClassNotFoundException;
}
