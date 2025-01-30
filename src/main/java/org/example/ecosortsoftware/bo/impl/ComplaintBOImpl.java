package org.example.ecosortsoftware.bo.impl;

import org.example.ecosortsoftware.DAO.DAOFactory;
import org.example.ecosortsoftware.DAO.custom.ComplaintDAO;
import org.example.ecosortsoftware.bo.ComplaintBO;
import org.example.ecosortsoftware.dto.ComplaintsDto;
import org.example.ecosortsoftware.dto.WardDto;
import org.example.ecosortsoftware.entity.Complaints;
import org.example.ecosortsoftware.entity.Ward;

import java.sql.SQLException;
import java.util.ArrayList;

public class ComplaintBOImpl implements ComplaintBO {
    ComplaintDAO complaintDAO= (ComplaintDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.COMPLAINT);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return complaintDAO.getNextId();
    }

    @Override
    public ComplaintsDto FindById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(ComplaintsDto complaintsdto) throws SQLException, ClassNotFoundException {
        return complaintDAO.save(new Complaints(complaintsdto.getComplaintId(),complaintsdto.getDescription(),complaintsdto.getStatus(),
                complaintsdto.getMunicipalId()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return complaintDAO.delete(id);
    }

    @Override
    public boolean update(ComplaintsDto complaintsdto) throws SQLException, ClassNotFoundException {
        return complaintDAO.update(new Complaints(complaintsdto.getComplaintId(),complaintsdto.getDescription(),complaintsdto.getStatus(),
                complaintsdto.getMunicipalId()));
    }

    @Override
    public ArrayList<ComplaintsDto> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {

        ArrayList<Complaints> allFromMunicipal = complaintDAO.getAllFromMunicipal(municipalId);

        ArrayList<ComplaintsDto> complaints = new ArrayList<>();

        for(Complaints complaint : allFromMunicipal){
            complaints.add(new ComplaintsDto(complaint.getComplaintId(),complaint.getDescription(),complaint.getStatus(),
                    complaint.getMunicipalId()));
        }
        return complaints;

    }
}
