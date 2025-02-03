package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.bo.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {}

    public static BOFactory getInstance() {
        return boFactory==null?boFactory=new BOFactory():boFactory;
    }


    public enum BOType {
        LOGIN,EMPLOYEE,VEHICLE,COMPLAINT,MUNICIPAL,INVENTORY,WARD,DISPOSAL,RECYCLING,PLACE_WASTE,SCHEDULE
    }
    public SuperBO getBO(BOType type) {
        switch (type) {
            case LOGIN:
                return new LoginBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            case COMPLAINT:
                return new ComplaintBOImpl();
            case MUNICIPAL:
                return new MunicipalBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
            case WARD:
                return new WardBOImpl();
            case DISPOSAL:
                return new DisposalBOImpl();
            case RECYCLING:
                return new RecyclingBOImpl();
            case PLACE_WASTE:
                return new PlaceWasteDataBOImpl();
            case SCHEDULE:
                return new ScheduleBOImpl();
            default:
                return null;
        }
    }
}
