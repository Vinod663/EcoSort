package org.example.ecosortsoftware.DAO;

import org.example.ecosortsoftware.DAO.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        return daoFactory==null? daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOType{
        LOGIN, EMPLOYEE,VEHICLE,COMPLAINT,MUNICIPAL,INVENTORY,WARD,DISPOSAL,RECYCLING,WASTE,SCHEDULE
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type){
            case LOGIN: return new LoginDAOimpl();
            case EMPLOYEE: return new EmployeeDAOimpl();
            case VEHICLE: return new VehicleDAOimpl();
            case COMPLAINT: return new ComplaintDAOimpl();
            case MUNICIPAL: return new MunicipalDAOimpl();
            case INVENTORY: return new InventoryDAOimpl();
            case WARD: return new WardDAOimpl();
            case DISPOSAL: return new DisposalDAOimpl();
            case RECYCLING: return new RecyclingDAOimpl();
            case WASTE: return new WasteCollectionDAOimpl();
            case SCHEDULE: return new ScheduleDAOimpl();
            default: return null;


        }

    }
}
