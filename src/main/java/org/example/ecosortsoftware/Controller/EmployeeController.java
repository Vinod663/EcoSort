package org.example.ecosortsoftware.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.ecosortsoftware.DTO.Tm.WardTm;
import org.example.ecosortsoftware.Model.EmployeeModel;
import org.example.ecosortsoftware.Model.WardModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private TextField EmailText;

    @FXML
    private Label EmpIdLab;

    @FXML
    private TableView<?> EmployeeTable;

    @FXML
    private TableColumn<?, ?> IdColumn;

    @FXML
    private TextField NameText;

    @FXML
    private ComboBox<String> WardNameCombo;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<?, ?> divisionColumn;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button updateBtn;

    @FXML
    void DeleteBtnAction(ActionEvent event) {

    }

    @FXML
    void saveBtnAction(ActionEvent event) {

    }

    @FXML
    void updateBtnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    WardModel wardModel=new WardModel();
    EmployeeModel employeeModel=new EmployeeModel();
    MunicipalController municipalController=new MunicipalController();
    String munId;

    public void loadWardNames() throws SQLException, ClassNotFoundException {
        String munId = municipalController.getMunicipalId();
        System.out.println("Loading Ward Names of "+munId);

        ArrayList<String> wardNames = wardModel.getAllWardNames(munId);
        ObservableList<String> observableWards = FXCollections.observableArrayList(wardNames);
        WardNameCombo.setItems(observableWards);

    }
    public void refreshPage() throws SQLException, ClassNotFoundException {
        loadWardNames();
        EmpIdLab.setText(String.valueOf(employeeModel.getNextEmpId()));
    }
}
