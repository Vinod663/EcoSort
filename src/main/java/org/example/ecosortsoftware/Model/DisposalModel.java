package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.dto.DisposalDto;
import org.example.ecosortsoftware.DAO.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisposalModel {
    public String getNextDisposalId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select disposal_id from disposal order by disposal_id desc limit 1;");

        if (resultSet.next()) {
            String lastColId = resultSet.getString(1);
            String subString = lastColId.substring(2);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;

            return String.format("DI0%03d", newIndex);
        }
        return "DI0001";
    }

    public boolean saveDisposal(DisposalDto disposalDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into disposal values(?,?,?,?)",
                disposalDto.getDisposalId(),disposalDto.getDisposalDate(),disposalDto.getWasteAmount(),disposalDto.getMunicipalId());
    }
}
