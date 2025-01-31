package org.example.ecosortsoftware.bo.impl;

import org.example.ecosortsoftware.DAO.DAOFactory;
import org.example.ecosortsoftware.DAO.custom.DisposalDAO;
import org.example.ecosortsoftware.bo.DisposalBO;
import org.example.ecosortsoftware.dto.DisposalDto;
import org.example.ecosortsoftware.entity.Disposal;

import java.sql.SQLException;

public class DisposalBOImpl implements DisposalBO {
    DisposalDAO disposalDAO= (DisposalDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DISPOSAL);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return disposalDAO.getNextId();
    }

    @Override
    public boolean save(DisposalDto disposaldto) throws SQLException, ClassNotFoundException {
        return disposalDAO.save(new Disposal(disposaldto.getDisposalId(),disposaldto.getDisposalDate(),
                disposaldto.getWasteAmount(),disposaldto.getMunicipalId()));
    }
}
