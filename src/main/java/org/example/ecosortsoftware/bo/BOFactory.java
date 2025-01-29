package org.example.ecosortsoftware.bo;

import org.example.ecosortsoftware.bo.impl.EmployeeBOImpl;
import org.example.ecosortsoftware.bo.impl.LoginBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {}

    public static BOFactory getInstance() {
        return boFactory==null?boFactory=new BOFactory():boFactory;
    }


    public enum BOType {
        LOGIN,EMPLOYEE
    }
    public SuperBO getBO(BOType type) {
        switch (type) {
            case LOGIN:
                return new LoginBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            default:
                return null;
        }
    }
}
