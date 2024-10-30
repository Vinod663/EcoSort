package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.Utill.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminModel {
    public boolean authenticate(String enteredUsername, String enteredPassword) throws SQLException, ClassNotFoundException {
        try{
                ResultSet result = CrudUtill.execute("SELECT * FROM admin WHERE username = ? AND password = ?", enteredUsername, enteredPassword);


            if (result.next()) {
                return true;  // Authentication successful
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Authentication failed
    }

    public boolean authenticateUsername(String enteredUsername) throws SQLException, ClassNotFoundException {
        try{
            ResultSet result = CrudUtill.execute("SELECT * FROM admin WHERE username = ?", enteredUsername);


            if (result.next()) {
                return true;  // Authentication successful
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Authentication failed
    }

    public boolean authenticatePsw(String enteredPassword) throws SQLException, ClassNotFoundException {
        try{
            ResultSet result = CrudUtill.execute("SELECT * FROM admin WHERE password = ?", enteredPassword);


            if (result.next()) {
                return true;  // Authentication successful
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Authentication failed
    }
}
