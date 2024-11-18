module org.example.ecosortsoftware {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires java.base;
    requires net.sf.jasperreports.core;



    opens org.example.ecosortsoftware.Controller to javafx.fxml;
    opens org.example.ecosortsoftware.DTO.Tm to javafx.base;
    //opens org.example.ecosortsoftware.DTO.TM to java.base;
    exports org.example.ecosortsoftware;
}