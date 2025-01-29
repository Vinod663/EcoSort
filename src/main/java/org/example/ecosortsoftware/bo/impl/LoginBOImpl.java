package org.example.ecosortsoftware.bo.impl;

import org.example.ecosortsoftware.DAO.DAOFactory;
import org.example.ecosortsoftware.DAO.custom.LoginDAO;
import org.example.ecosortsoftware.bo.LoginBO;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {
    LoginDAO loginDAO= (LoginDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LOGIN);

    @Override
    public boolean authenticate(String enteredUsername, String enteredPassword) throws SQLException, ClassNotFoundException {
        return loginDAO.authenticate(enteredUsername, enteredPassword);
    }

    @Override
    public boolean authenticateUsername(String enteredUsername) throws SQLException, ClassNotFoundException {
        return loginDAO.authenticateUsername(enteredUsername);
    }

    @Override
    public boolean authenticatePsw(String enteredPassword) throws SQLException, ClassNotFoundException {
        return authenticatePsw(enteredPassword);
    }
}
