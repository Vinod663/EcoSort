package org.example.ecosortsoftware.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class VehicleController {

    @FXML
    private Button DeleteBtn;

    @FXML
    private TableColumn<?, ?> EmployeeIdCol;

    @FXML
    private TableColumn<?, ?> LicensePlateCol;

    @FXML
    private TextField LicensePlateText;

    @FXML
    private Button SaveBtn;

    @FXML
    private Button UpdateBtn;

    @FXML
    private Label VehicleIdLabel;

    @FXML
    private TableView<?> VehicleTable;

    @FXML
    private TableColumn<?, ?> VehicleTypeCol;

    @FXML
    private ComboBox<?> employeeIdCombo;

    @FXML
    private TextField employeeNameText;

    @FXML
    private TableColumn<?, ?> vehicleIdCol;

    @FXML
    private TextField vehicletypeText;

    @FXML
    void DeleteBtnAction(ActionEvent event) {

    }

    @FXML
    void SaveBtnAction(ActionEvent event) {

    }

    @FXML
    void UpdateBtnAction(ActionEvent event) {

    }

}
