package org.example.ecosortsoftware.DAO.custom.impl;

import org.example.ecosortsoftware.DAO.SQLUtil;
import org.example.ecosortsoftware.DAO.custom.LoginDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDAOimpl implements LoginDAO {

    @Override
    public boolean authenticate(String enteredUsername, String enteredPassword) throws SQLException, ClassNotFoundException {
        try{
            ResultSet result = SQLUtil.execute("SELECT * FROM admin WHERE username = ? AND password = ?", enteredUsername, enteredPassword);


            if (result.next()) {
                return true;  // Authentication successful
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Authentication failed
    }
    @Override
    public boolean authenticateUsername(String enteredUsername) throws SQLException, ClassNotFoundException {
        try{
            ResultSet result = SQLUtil.execute("SELECT * FROM admin WHERE username = ?", enteredUsername);


            if (result.next()) {
                return true;  // Authentication successful
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Authentication failed
    }

    @Override
    public boolean authenticatePsw(String enteredPassword) throws SQLException, ClassNotFoundException {
        try{
            ResultSet result = SQLUtil.execute("SELECT * FROM admin WHERE password = ?", enteredPassword);


            if (result.next()) {
                return true;  // Authentication successful
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Authentication failed
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public Object FindById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Object dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Object dto) throws SQLException, ClassNotFoundException {
        return false;
    }


}
