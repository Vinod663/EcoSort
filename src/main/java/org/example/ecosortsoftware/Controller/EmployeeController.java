package org.example.ecosortsoftware.Controller;

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
import org.example.ecosortsoftware.DB.DBConnection;
import org.example.ecosortsoftware.DTO.EmployeeDto;
import org.example.ecosortsoftware.DTO.Tm.EmployeeTm;
import org.example.ecosortsoftware.Model.EmployeeModel;
import org.example.ecosortsoftware.Model.WardModel;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EmployeeController implements Initializable {

    public Button ResetBtn;
    public TextField PhoneNumberText;
    public TableColumn<EmployeeTm, String> PhoneNumberCol;
    public Button employeereportBtn;
    @FXML
    private TextField EmailText;

    @FXML
    private Label EmpIdLab;

    @FXML
    private TableView<EmployeeTm> EmployeeTable;

    @FXML
    private TableColumn<EmployeeTm, String> IdColumn;

    @FXML
    private TextField NameText;


    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<EmployeeTm, String> emailColumn;

    @FXML
    private TableColumn<EmployeeTm, String> nameColumn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button updateBtn;

    @FXML
    void DeleteBtnAction(ActionEvent event) {
        try{
            String id=EmpIdLab.getText();

            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if(buttonType.isPresent()&&buttonType.get()==ButtonType.YES){
                boolean result=employeeModel.DeleteEmployee(id);

                if(result){
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully", ButtonType.OK).show();
                }
                else{
                    new Alert(Alert.AlertType.ERROR, "Employee Deletion Failed Employee Not Found!", ButtonType.OK).show();
                }
            }


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error Employee Deletion Failed", ButtonType.OK).show();
        }
    }

    @FXML
    void saveBtnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String namePattern = "^[A-Za-z ]+$";
        boolean isValidName = NameText.getText().matches(namePattern);
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        boolean isValidEmail = EmailText.getText().matches(emailPattern);

        if (!isValidName) {
            System.out.println(NameText.getStyle());
            NameText.setStyle("-fx-text-box-border: red; -fx-text-inner-color: red;");
            System.out.println("Invalid name........");

        }
        if (isValidName) {
            System.out.println(NameText.getStyle());
            NameText.setStyle("-fx-text-box-border: green; -fx-text-inner-color: green;");
            System.out.println("valid name........");

        }
//        if (!isValidEmail) {
//            EmailText.setStyle("-fx-text-box-border: red; -fx-text-inner-color: red;");
//
//        }
//        if (isValidEmail) {
//            EmailText.setStyle("-fx-text-box-border: green; -fx-text-inner-color: green;");
//
//        }

        if (isValidName /*&& isValidEmail*/ ) {
            EmployeeDto employeeDto=new EmployeeDto();
            employeeDto.setEmail(EmailText.getText());
            employeeDto.setEmployeeName(NameText.getText());
            employeeDto.setEmployeeId(EmpIdLab.getText());
            employeeDto.setMunicipalId(munId);
            employeeDto.setPhoneNumber(PhoneNumberText.getText());

            boolean isSaved = employeeModel.saveEmployee(employeeDto);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee Saved", ButtonType.OK).show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Saved.Duplicate ID or Something went wrong!", ButtonType.OK).show();
            }
        }




    }

    @FXML
    void updateBtnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String namePattern = "^[A-Za-z ]+$";
        boolean isValidName = NameText.getText().matches(namePattern);
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        boolean isValidEmail = EmailText.getText().matches(emailPattern);

        if (!isValidName) {
            System.out.println(NameText.getStyle());
            NameText.setStyle("-fx-text-box-border: red; -fx-text-inner-color: red;");
            System.out.println("Invalid name........");

        }
        if (isValidName) {
            System.out.println(NameText.getStyle());
            NameText.setStyle("-fx-text-box-border: green; -fx-text-inner-color: green;");
            System.out.println("valid name........");

        }
//        if (!isValidEmail) {
//            EmailText.setStyle("-fx-text-box-border: red; -fx-text-inner-color: red;");
//
//        }
//        if (isValidEmail) {
//            EmailText.setStyle("-fx-text-box-border: green; -fx-text-inner-color: green;");
//
//        }

        if (isValidName /*&& isValidEmail*/ ) {
            EmployeeDto employeeDto=new EmployeeDto();
            employeeDto.setEmail(EmailText.getText());
            employeeDto.setEmployeeName(NameText.getText());
            employeeDto.setEmployeeId(EmpIdLab.getText());
            employeeDto.setMunicipalId(munId);
            employeeDto.setPhoneNumber(PhoneNumberText.getText());

            boolean isUpdated = employeeModel.updateEmployee(employeeDto);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee Updated", ButtonType.OK).show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Updated.Something went wrong!", ButtonType.OK).show();
            }
        }
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
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        PhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        munId=municipalController.getMunicipalId();
//        System.out.println("check "+munId);
    }
    WardModel wardModel=new WardModel();
    EmployeeModel employeeModel=new EmployeeModel();
    MunicipalController municipalController=new MunicipalController();
    String munId;


//    public void loadWardNames() throws SQLException, ClassNotFoundException {
//        String munId = municipalController.getMunicipalId();
//        System.out.println("Loading Ward Names of "+munId);
//
//        ArrayList<String> wardNames = wardModel.getAllWardNames(munId);
//        ObservableList<String> observableWards = FXCollections.observableArrayList(wardNames);
//        WardNameCombo.setItems(observableWards);
//
//    }
    public void refreshPage() throws SQLException, ClassNotFoundException {
//        loadWardNames();
        NameText.setText("");
        NameText.setStyle("");
        EmailText.setText("");
        EmailText.setStyle("");
        PhoneNumberText.setText("");

        saveBtn.setDisable(false);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
        EmpIdLab.setText(String.valueOf(employeeModel.getNextEmpId()));
        loadTable();
    }

    public void loadTable() throws SQLException, ClassNotFoundException {
        ObservableList <EmployeeTm> employeeTms= FXCollections.observableArrayList();

        ArrayList<EmployeeTm> all = EmployeeModel.getAll(municipalController.getMunicipalId());

        for(EmployeeTm employeeTm:all){
            employeeTms.add(employeeTm);
        }
        EmployeeTable.setItems(employeeTms);

    }

    public void EmployeeTableClickAction(MouseEvent mouseEvent) {
        EmployeeTm selectedItem=EmployeeTable.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            EmpIdLab.setText(selectedItem.getEmployeeId());
            NameText.setText(selectedItem.getEmployeeName());
            EmailText.setText(selectedItem.getEmail());
            PhoneNumberText.setText(selectedItem.getPhoneNumber());
            updateBtn.setDisable(false);
            deleteBtn.setDisable(false);
            saveBtn.setDisable(true);
        }
    }

    public void ResetBtnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void EmployeeReportAction(ActionEvent actionEvent) {
        if(munId==null){
            new Alert(Alert.AlertType.ERROR, "Select Municipal First!", ButtonType.OK).show();
            return;
        }
        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/Report/EmployeeReport.jrxml")
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
