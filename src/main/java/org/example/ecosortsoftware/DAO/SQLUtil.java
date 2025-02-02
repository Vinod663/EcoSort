package org.example.ecosortsoftware.DAO;

import org.example.ecosortsoftware.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtil {
    public static <T>T execute(String sql,Object...obj) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement pst=connection.prepareStatement(sql);

        for(int i=0;i<obj.length;i++){
            pst.setObject(i+1,obj[i]);

        }
        try{
            if(sql.startsWith("select")||sql.startsWith("SELECT")){
                ResultSet resultSet=pst.executeQuery();
                return (T)resultSet;
            }
            else{
                int i= pst.executeUpdate();
                boolean isSaved=i>0;
                return (T)((Boolean)isSaved);
            }

        }

        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            throw e; // Rethrow for logging in the calling method
        }


    }
}
