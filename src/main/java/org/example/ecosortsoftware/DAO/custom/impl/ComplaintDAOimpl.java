package org.example.ecosortsoftware.DAO.custom.impl;

import org.example.ecosortsoftware.DAO.SQLUtil;
import org.example.ecosortsoftware.DAO.custom.ComplaintDAO;
import org.example.ecosortsoftware.view.tdm.ComplaintsTm;
import org.example.ecosortsoftware.entity.Complaints;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComplaintDAOimpl implements ComplaintDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select complaint_id from complaint order by complaint_id desc limit 1;");

        if (resultSet.next()) {
            String lastColId = resultSet.getString(1);
            String subString = lastColId.substring(2);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;

            return String.format("CP0%03d", newIndex);
        }
        return "CP0001";
    }

    @Override
    public Complaints FindById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Complaints complaints) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into complaint values(?,?,?,?)",
                complaints.getComplaintId(),complaints.getDescription(),complaints.getStatus(),complaints.getMunicipalId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM complaint WHERE complaint_id=?",id);
    }

    @Override
    public boolean update(Complaints complaints) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update complaint set description=?, status=?, municipalId=? where complaint_id=?",
                complaints.getDescription(),complaints.getStatus(),complaints.getMunicipalId(),complaints.getComplaintId());
    }

    @Override
    public ArrayList<Complaints> getAllFromMunicipal(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select * from complaint where municipalId=?", municipalId);
        ArrayList<Complaints> compTms = new ArrayList<>();

        while (resultSet.next()) {
            Complaints cpTm = new Complaints();

            cpTm.setComplaintId(resultSet.getString("complaint_id"));
            cpTm.setDescription(resultSet.getString("description"));
            cpTm.setStatus(resultSet.getString("status"));
            cpTm.setMunicipalId(resultSet.getString("municipalId"));

            compTms.add(cpTm);
        }
        return compTms;
    }
}
