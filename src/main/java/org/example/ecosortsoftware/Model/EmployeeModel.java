package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.Utill.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel {
    public String getNextEmpId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select employee_id from employee order by employee_id desc limit 1;");

        if (resultSet.next()) {
            String lastEmpId = resultSet.getString(1);
            String subString = lastEmpId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;

            return String.format("E%03d", newIndex);
        }
        return "E0001";
    }
}
