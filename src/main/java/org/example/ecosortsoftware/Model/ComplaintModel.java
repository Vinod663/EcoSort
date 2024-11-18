package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.DTO.ComplaintsDto;
import org.example.ecosortsoftware.DTO.Tm.ComplaintsTm;
import org.example.ecosortsoftware.DTO.Tm.EmployeeTm;
import org.example.ecosortsoftware.Utill.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComplaintModel {
    public static ArrayList<ComplaintsTm> getAll(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select * from complaint where municipalId=?", municipalId);
        ArrayList<ComplaintsTm> compTms = new ArrayList<>();

        while (resultSet.next()) {
            ComplaintsTm cpTm = new ComplaintsTm();

            cpTm.setComplaintId(resultSet.getString("complaint_id"));
            cpTm.setDescription(resultSet.getString("description"));
            cpTm.setStatus(resultSet.getString("status"));
            cpTm.setMunicipalId(resultSet.getString("municipalId"));

            compTms.add(cpTm);
        }
        return compTms;
    }

    public String getNextCpId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select complaint_id from complaint order by complaint_id desc limit 1;");

        if (resultSet.next()) {
            String lastColId = resultSet.getString(1);
            String subString = lastColId.substring(2);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;

            return String.format("CP0%03d", newIndex);
        }
        return "CP0001";
    }

    public boolean saveComplaint(ComplaintsDto complaintsDto) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("insert into complaint values(?,?,?,?)",
                complaintsDto.getComplaintId(),complaintsDto.getDescription(),complaintsDto.getStatus(),complaintsDto.getMunicipalId());
    }

    public boolean updateComplaint(ComplaintsDto complaintsDto) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("update complaint set description=?, status=?, municipalId=? where complaint_id=?",
                complaintsDto.getDescription(),complaintsDto.getStatus(),complaintsDto.getMunicipalId(),complaintsDto.getComplaintId());
    }

    public boolean DeleteComplaint(String id) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("DELETE FROM complaint WHERE complaint_id=?",id);
    }
}
