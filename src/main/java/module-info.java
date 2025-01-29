module org.example.ecosortsoftware {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires java.base;
    requires net.sf.jasperreports.core;
    requires java.mail;



    opens org.example.ecosortsoftware.controller to javafx.fxml;
    opens org.example.ecosortsoftware.dto.Tm to javafx.base;
    opens org.example.ecosortsoftware.view.tdm to javafx.fxml;
    //opens org.example.ecosortsoftware.DTO.TM to java.base;
    exports org.example.ecosortsoftware;
    exports org.example.ecosortsoftware.view.tdm;
}