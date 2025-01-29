package org.example.ecosortsoftware.DAO;

import org.example.ecosortsoftware.view.tdm.VehicleTm;
import org.example.ecosortsoftware.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    String getNextId() throws SQLException, ClassNotFoundException;
    T FindById(String selectedId) throws SQLException, ClassNotFoundException;
    boolean save(T dto) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    boolean update(T dto) throws SQLException, ClassNotFoundException;
    ArrayList<T> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException;
}
