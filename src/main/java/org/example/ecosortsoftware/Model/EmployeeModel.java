package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.DTO.EmployeeDto;
import org.example.ecosortsoftware.DTO.Tm.EmployeeTm;
import org.example.ecosortsoftware.DTO.Tm.SheduleTm;
import org.example.ecosortsoftware.DTO.VehicleDto;
import org.example.ecosortsoftware.Utill.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public static ArrayList<String> getAllEmpIds(String munId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtill.execute("select * from employee where municipal_id=?", munId);
        ArrayList<String> empNames = new ArrayList<>();

        while (result.next()) {
            empNames.add(result.getString("employee_id"));
        }
        return empNames;
    }

    public static ArrayList<EmployeeTm> getAll(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select * from employee where municipal_id=?", municipalId);
        ArrayList<EmployeeTm> empTms = new ArrayList<>();

        while (resultSet.next()) {
            EmployeeTm empTm = new EmployeeTm();

            empTm.setEmployeeId(resultSet.getString("employee_id"));
            empTm.setEmployeeName(resultSet.getString("name"));
            empTm.setEmail(resultSet.getString("email"));
            empTm.setMunicipalId(resultSet.getString("municipal_id"));
            empTm.setPhoneNumber(resultSet.getString("phoneNumber"));

            empTms.add(empTm);
        }
        return empTms;
    }

    public String getNextEmpId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select employee_id from employee order by employee_id desc limit 1;");

        if (resultSet.next()) {
            String lastEmpId = resultSet.getString(1);
            String subString = lastEmpId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;

            return String.format("E0%03d", newIndex);
        }
        return "E0001";
    }

    public EmployeeDto FindById(String selectedId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtill.execute("select * from employee where employee_id=?", selectedId);
        if (rst.next()) {
            return new EmployeeDto(
                    rst.getString(1)
                    ,rst.getString(2)
                    ,rst.getString(3)
                    ,rst.getString(4)
                    ,rst.getString(5)
            );
        }
        return null;
    }

    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("insert into employee values(?,?,?,?,?)",
                employeeDto.getEmployeeId(),employeeDto.getEmployeeName(),employeeDto.getEmail(),employeeDto.getMunicipalId(),employeeDto.getPhoneNumber());
    }

    public boolean DeleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("DELETE FROM employee WHERE employee_id=?",id);
    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("Update employee SET name=?, email=?, municipal_id=?, phoneNumber=? WHERE employee_id=?",
        employeeDto.getEmployeeName(),employeeDto.getEmail(),employeeDto.getMunicipalId(),employeeDto.getPhoneNumber(),employeeDto.getEmployeeId());
    }
}
