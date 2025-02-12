package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.dto.EmployeeDto;
import org.example.ecosortsoftware.view.tdm.EmployeeTm;
import org.example.ecosortsoftware.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO{
    ArrayList<String> getAllEmpIds(String munId) throws SQLException, ClassNotFoundException;

    ArrayList<EmployeeDto> getAll(String municipalId) throws SQLException, ClassNotFoundException;

    String getNextId() throws SQLException, ClassNotFoundException;

    EmployeeDto FindById(String selectedId) throws SQLException, ClassNotFoundException;

    boolean save(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    boolean update(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException;
}
