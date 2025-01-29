package org.example.ecosortsoftware.DAO.custom.impl;

import org.example.ecosortsoftware.DAO.SQLUtil;
import org.example.ecosortsoftware.DAO.custom.EmployeeDAO;
import org.example.ecosortsoftware.view.tdm.EmployeeTm;
import org.example.ecosortsoftware.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOimpl implements EmployeeDAO {

    public static ArrayList<String> getAllEmpIds(String munId) throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.execute("select * from employee where municipal_id=?", munId);
        ArrayList<String> empNames = new ArrayList<>();

        while (result.next()) {
            empNames.add(result.getString("employee_id"));
        }
        return empNames;
    }

    public static ArrayList<EmployeeTm> getAll(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select * from employee where municipal_id=?", municipalId);
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

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select employee_id from employee order by employee_id desc limit 1;");

        if (resultSet.next()) {
            String lastEmpId = resultSet.getString(1);
            String subString = lastEmpId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;

            return String.format("E0%03d", newIndex);
        }
        return "E0001";
    }

    @Override
    public Employee FindById(String selectedId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from employee where employee_id=?", selectedId);
        if (rst.next()) {
            return new Employee(
                    rst.getString(1)
                    ,rst.getString(2)
                    ,rst.getString(3)
                    ,rst.getString(4)
                    ,rst.getString(5)
            );
        }
        return null;
    }

    @Override
    public boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into employee values(?,?,?,?,?)",
                employee.getEmployeeId(),employee.getEmployeeName(),employee.getEmail(),employee.getMunicipalId(),employee.getPhoneNumber());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employee WHERE employee_id=?",id);
    }

    @Override
    public boolean update(Employee employee) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("Update employee SET name=?, email=?, municipal_id=?, phoneNumber=? WHERE employee_id=?",
                employee.getEmployeeName(),employee.getEmail(),employee.getMunicipalId(),employee.getPhoneNumber(),employee.getEmployeeId());
    }

    @Override
    public ArrayList<Employee> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        return null;
    }
}
