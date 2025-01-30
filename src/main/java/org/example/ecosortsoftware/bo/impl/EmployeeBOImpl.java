package org.example.ecosortsoftware.bo.impl;

import org.example.ecosortsoftware.DAO.DAOFactory;
import org.example.ecosortsoftware.DAO.custom.EmployeeDAO;
import org.example.ecosortsoftware.DAO.custom.impl.EmployeeDAOimpl;
import org.example.ecosortsoftware.bo.EmployeeBO;
import org.example.ecosortsoftware.dto.EmployeeDto;
import org.example.ecosortsoftware.view.tdm.EmployeeTm;
import org.example.ecosortsoftware.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO= (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public ArrayList<String> getAllEmpIds(String munId) throws SQLException, ClassNotFoundException {
        return EmployeeDAOimpl.getAllEmpIds(munId);
    }

    @Override
    public ArrayList<EmployeeTm> getAll(String municipalId) throws SQLException, ClassNotFoundException {
        return EmployeeDAOimpl.getAll(municipalId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return employeeDAO.getNextId();
    }

    @Override
    public EmployeeDto FindById(String selectedId) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.FindById(selectedId);
        return new EmployeeDto(employee.getEmployeeId(),employee.getEmployeeName(),employee.getEmail(),
                employee.getMunicipalId(),employee.getPhoneNumber());
    }

    @Override
    public boolean save(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(employeeDto.getEmployeeId(),employeeDto.getEmployeeName(),
                employeeDto.getEmail(),employeeDto.getMunicipalId(),employeeDto.getPhoneNumber()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public boolean update(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(employeeDto.getEmployeeId(),employeeDto.getEmployeeName(),
                employeeDto.getEmail(),employeeDto.getMunicipalId(),employeeDto.getPhoneNumber()));
    }
}
