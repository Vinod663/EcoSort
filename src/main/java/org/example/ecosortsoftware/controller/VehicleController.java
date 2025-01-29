package org.example.ecosortsoftware.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.example.ecosortsoftware.db.DBConnection;
import org.example.ecosortsoftware.dto.EmployeeDto;
import org.example.ecosortsoftware.dto.Tm.VehicleTm;
import org.example.ecosortsoftware.dto.VehicleDto;
import org.example.ecosortsoftware.Model.EmployeeModel;
import org.example.ecosortsoftware.Model.VehicleModel;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VehicleController implements Initializable {

    public Label EmployeeNameLabe;
    public Button ResetBtn;
    public Button vehicleReportBtn;
    @FXML
    private Button DeleteBtn;

    @FXML
    private TableColumn<VehicleTm, String> EmployeeIdCol;

    @FXML
    private TableColumn<VehicleTm, String> LicensePlateCol;

    @FXML
    private TextField LicensePlateText;

    @FXML
    private Button SaveBtn;

    @FXML
    private Button UpdateBtn;

    @FXML
    private Label VehicleIdLabel;

    @FXML
    private TableView<VehicleTm> VehicleTable;

    @FXML
    private TableColumn<VehicleTm, String> VehicleTypeCol;

    @FXML
    private ComboBox<String> employeeIdCombo;

    @FXML
    private TextField employeeNameText;

    @FXML
    private TableColumn<VehicleTm, String> vehicleIdCol;

    @FXML
    private TextField vehicletypeText;

    VehicleModel vehicleModel=new VehicleModel();
    @FXML
    void DeleteBtnAction(ActionEvent event) {
        try{
            String id=VehicleIdLabel.getText();

            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if(buttonType.isPresent()&&buttonType.get()==ButtonType.YES){
                boolean result=vehicleModel.DeleteVehicle(id);

                if(result){
                    refershPage();
                    new Alert(Alert.AlertType.INFORMATION, "Vehicle Deleted Successfully", ButtonType.OK).show();
                }
                else{
                    new Alert(Alert.AlertType.ERROR, "Vehicle Deletion Failed Vehicle Not Found!", ButtonType.OK).show();
                }
            }


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error Vehicle Deletion Failed", ButtonType.OK).show();
        }
    }

    @FXML
    void SaveBtnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        VehicleDto vehicleDto=new VehicleDto();
        vehicleDto.setLicensePlate(LicensePlateText.getText());
        vehicleDto.setVehicleId(VehicleIdLabel.getText());
        vehicleDto.setVehicleType(vehicletypeText.getText());
        vehicleDto.setEmployeeId(employeeIdCombo.getValue());
        vehicleDto.setMunicipalId(munId);

        boolean isSaved = vehicleModel.saveVehicle(vehicleDto);
        if (isSaved) {
            refershPage();
            new Alert(Alert.AlertType.INFORMATION, "Vehicle Saved", ButtonType.OK).show();

        } else {
            new Alert(Alert.AlertType.ERROR, "Vehicle Not Saved.Duplicate ID or Something went wrong!", ButtonType.OK).show();
        }
    }

    @FXML
    void UpdateBtnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        VehicleDto vehicleDto=new VehicleDto();
        vehicleDto.setLicensePlate(LicensePlateText.getText());
        vehicleDto.setVehicleId(VehicleIdLabel.getText());
        vehicleDto.setVehicleType(vehicletypeText.getText());
        vehicleDto.setEmployeeId(employeeIdCombo.getValue());
        vehicleDto.setMunicipalId(munId);

        boolean isUpdated = vehicleModel.updateVehicle(vehicleDto);
        if (isUpdated) {
            refershPage();
            new Alert(Alert.AlertType.INFORMATION, "Vehicle Updated", ButtonType.OK).show();

        } else {
            new Alert(Alert.AlertType.ERROR, "Vehicle Not Updated.Something went wrong!", ButtonType.OK).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refershPage();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        vehicleIdCol.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        EmployeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        VehicleTypeCol.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        LicensePlateCol.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
    }

    String munId;

    public void refershPage() throws SQLException, ClassNotFoundException {
        vehicletypeText.clear();
        employeeIdCombo.getItems().clear();
        EmployeeNameLabe.setText("");
        LicensePlateText.clear();
        VehicleIdLabel.setText(vehicleModel.getNextVehicleId());

        loadEmployeeIds();
        loadTable();

        munId=municipalController.getMunicipalId();
        SaveBtn.setDisable(false);
        UpdateBtn.setDisable(true);
        DeleteBtn.setDisable(true);


    }

    public void loadTable() throws SQLException, ClassNotFoundException {
        ObservableList <VehicleTm> vehicleTms= FXCollections.observableArrayList();

        ArrayList<VehicleTm> all = vehicleModel.getAll(municipalController.getMunicipalId());

        for(VehicleTm vehicleTm:all){
            vehicleTms.add(vehicleTm);
        }
        VehicleTable.setItems(vehicleTms);

    }

    MunicipalController municipalController=new MunicipalController();
    public void loadEmployeeIds() throws SQLException, ClassNotFoundException {
        String munId = municipalController.getMunicipalId();
        System.out.println("Loading Employee Ids of "+munId);

        ArrayList<String> EmpIds = EmployeeModel.getAllEmpIds(munId);
        ObservableList<String> observableEmp = FXCollections.observableArrayList(EmpIds);
        employeeIdCombo.setItems(observableEmp);
    }
    private EmployeeModel employeeModel=new EmployeeModel();

    public void EmployeeComboAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedId = employeeIdCombo.getSelectionModel().getSelectedItem();
        EmployeeDto empDto=employeeModel.FindById(selectedId);

        if (empDto!=null){
            EmployeeNameLabe.setText(empDto.getEmployeeName());
        }
    }

    public void ResetBtnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refershPage();
    }

    public void VehicleTableClickAction(MouseEvent mouseEvent) {
        VehicleTm selectedItem=VehicleTable.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            VehicleIdLabel.setText(selectedItem.getVehicleId());
            vehicletypeText.setText(selectedItem.getVehicleType());
            employeeIdCombo.getSelectionModel().select(selectedItem.getEmployeeId());
            LicensePlateText.setText(selectedItem.getLicensePlate());
            UpdateBtn.setDisable(false);
            DeleteBtn.setDisable(false);
            SaveBtn.setDisable(true);
        }
    }

    public void VehicleReportAction(ActionEvent actionEvent) {
        if(munId==null){
            new Alert(Alert.AlertType.ERROR, "Select Municipal First!", ButtonType.OK).show();
            return;
        }
        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/Report/VehicleReport.jrxml")
            );
            Connection connection= DBConnection.getInstance().getConnection();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("P_Date", LocalDate.now().toString());
            parameters.put("P_Municipal_Id",munId);
            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String time = now.format(formatter);
            parameters.put("P_Time", time);

            JasperPrint jasperPrint = JasperFillManager.fillReport(////////
                    jasperReport,
                    parameters,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);

        }
        catch(JRException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to Generate Report!..", ButtonType.OK).show();
        }
        catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, "Fail to Connect database", ButtonType.OK).show();
        }
        catch(ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, "Class not founf", ButtonType.OK).show();
        }
    }
}
