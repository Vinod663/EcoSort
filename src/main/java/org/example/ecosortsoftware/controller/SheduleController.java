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
import org.example.ecosortsoftware.bo.BOFactory;
import org.example.ecosortsoftware.bo.WardBO;
import org.example.ecosortsoftware.db.DBConnection;
import org.example.ecosortsoftware.dto.SheduleDto;
import org.example.ecosortsoftware.dto.Tm.SheduleTm;
import org.example.ecosortsoftware.dto.WardDto;
import org.example.ecosortsoftware.view.tdm.WardTm;
import org.example.ecosortsoftware.Model.SheduleModel;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class SheduleController implements Initializable {
    WardBO wardBO= (WardBO) BOFactory.getInstance().getBO(BOFactory.BOType.WARD);

    public Button SheduleReportBtn;
    @FXML
    private TableColumn<SheduleTm, String> DegradableWasteCol;

    @FXML
    private CheckBox DegradeDailyCheck;

    @FXML
    private CheckBox DegradeFridCheck;

    @FXML
    private CheckBox DegradeMondayCheck;

    @FXML
    private CheckBox DegradeSaturCheck;

    @FXML
    private CheckBox DegradeSunCheck;

    @FXML
    private CheckBox DegradeThurCheck;

    @FXML
    private CheckBox DegradeTueCheck;

    @FXML
    private CheckBox DegradeWedCheck;

    @FXML
    private CheckBox NonRecDailyCheck;

    @FXML
    private CheckBox NonRecFridCheck;

    @FXML
    private CheckBox NonRecMondayCheck;

    @FXML
    private CheckBox NonRecSaturCheck;

    @FXML
    private CheckBox NonRecSunCheck;

    @FXML
    private CheckBox NonRecThurCheck;

    @FXML
    private CheckBox NonRecTueCheck;

    @FXML
    private CheckBox NonRecWedCheck;

    @FXML
    private TableColumn<SheduleTm, String> NonRecyclableWasteCol;

    @FXML
    private CheckBox RecyDailyCheck;

    @FXML
    private CheckBox RecyFridCheck;

    @FXML
    private CheckBox RecyMondayCheck;

    @FXML
    private CheckBox RecySaturCheck;

    @FXML
    private CheckBox RecySunCheck;

    @FXML
    private CheckBox RecyThurCheck;

    @FXML
    private CheckBox RecyTueCheck;

    @FXML
    private CheckBox RecyWedCheck;

    @FXML
    private TableColumn<SheduleTm, String> RecyclableWasteCol;

    @FXML
    private Button SaveBtn;

    @FXML
    private TableView<SheduleTm> SheduleTable;

    @FXML
    private TableColumn<SheduleTm, String> WardIdCol;

    @FXML
    private Label WardIdLab;

    @FXML
    private TableColumn<SheduleTm, String> WardNameCol;

    @FXML
    private Label WardNameLab;

    SheduleModel sheduleModel = new SheduleModel();
    @FXML
    void OnClickTable(MouseEvent event) {
        SheduleTm selectedSchedule = SheduleTable.getSelectionModel().getSelectedItem();
        if (selectedSchedule != null) {
            SaveBtn.setDisable(false);
            WardIdLab.setText(selectedSchedule.getDivisionId());
            WardNameLab.setText(selectedSchedule.getDepot());

            String degradable=selectedSchedule.getDegradableWaste();
            String recyclable=selectedSchedule.getRecyclableWaste();
            String nonRecyclable=selectedSchedule.getNonRecyclableWaste();

            if(degradable!=null) {
                setCheckBoxesFromString(degradable, DegradeDailyCheck, DegradeMondayCheck, DegradeTueCheck, DegradeWedCheck,
                        DegradeThurCheck, DegradeFridCheck, DegradeSaturCheck, DegradeSunCheck);
            }
            else{
                DegradableCheckBoxFalse();
            }

            if(recyclable!=null) {
                setCheckBoxesFromString(recyclable, RecyDailyCheck, RecyMondayCheck, RecyTueCheck, RecyWedCheck,
                        RecyThurCheck, RecyFridCheck, RecySaturCheck, RecySunCheck);
            }
            else{
                RecyclableCheckBoxFalse();
            }

            if(nonRecyclable!=null) {
                setCheckBoxesFromString(nonRecyclable, NonRecDailyCheck, NonRecMondayCheck, NonRecTueCheck, NonRecWedCheck,
                        NonRecThurCheck, NonRecFridCheck, NonRecSaturCheck, NonRecSunCheck);
            }
            else{
                NonRecyclableCheckBoxFalse();
            }
//            DegradeDailyCheck.setSelected(true);////
//            System.out.println(DegradeDailyCheck.isSelected());///////////
        }
    }

    public void setCheckBoxesFromString(String daysString, CheckBox dailyCheck, CheckBox... dayChecks) {
        if ("Daily".equalsIgnoreCase(daysString)) {
            dailyCheck.setSelected(true);
            // Unselect all other day checkboxes if "Daily" is selected
            for (CheckBox dayCheck : dayChecks) {
                dayCheck.setSelected(false);
            }
        } else {
            dailyCheck.setSelected(false);
            List<String> selectedDays = List.of(daysString.split(","));
            for (CheckBox dayCheck : dayChecks) {
                dayCheck.setSelected(selectedDays.contains(dayCheck.getText()));
            }
        }
    }

    String munId;
    @FXML
    void SaveBtnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String municipalId=munId;
        String divisionId=WardIdLab.getText();
        String depot=WardNameLab.getText();
        String degradable=getSelectedDaysAsString(DegradeDailyCheck,DegradeMondayCheck,DegradeTueCheck,DegradeWedCheck,
                                                  DegradeThurCheck,DegradeFridCheck,DegradeSaturCheck,DegradeSunCheck);
        String recyclable=getSelectedDaysAsString(RecyDailyCheck,RecyMondayCheck,RecyTueCheck,RecyWedCheck,
                                                 RecyThurCheck,RecyFridCheck,RecySaturCheck,RecySunCheck);
        String nonRecyclable=getSelectedDaysAsString(NonRecDailyCheck,NonRecMondayCheck,NonRecTueCheck,NonRecWedCheck,
                                                    NonRecThurCheck,NonRecFridCheck,NonRecSaturCheck,NonRecSunCheck);

        System.out.println(municipalId+" "+divisionId+" "+depot);
        System.out.println("Degradable: " + degradable);
        System.out.println("Recyclable: " + recyclable);
        System.out.println("Non-Recyclable: " + nonRecyclable);

        SheduleDto sheduleDto=new SheduleDto(municipalId,divisionId,depot,degradable,recyclable,nonRecyclable);
        boolean isUpdate=sheduleModel.updateShedule(sheduleDto);
        if(isUpdate){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION,"Shedule Updated",ButtonType.OK).show();
        }

        else{
            new Alert(Alert.AlertType.ERROR,"Shedule Not Updated",ButtonType.OK).show();
        }



    }

    private String getSelectedDaysAsString(CheckBox dailyCheck, CheckBox... dayChecks) {
        if (dailyCheck.isSelected()) {
            return "Daily";
        } else {
            List<String> selectedDays = new ArrayList<>();
            for (CheckBox dayCheck : dayChecks) {
                if (dayCheck.isSelected()) {
                    selectedDays.add(dayCheck.getText());  // assuming each checkbox text is the day name
                }
            }
            return String.join(",", selectedDays);
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

        WardIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        WardNameCol.setCellValueFactory(new PropertyValueFactory<>("depot"));
        DegradableWasteCol.setCellValueFactory(new PropertyValueFactory<>("degradableWaste"));
        RecyclableWasteCol.setCellValueFactory(new PropertyValueFactory<>("recyclableWaste"));
        NonRecyclableWasteCol.setCellValueFactory(new PropertyValueFactory<>("nonRecyclableWaste"));
    }

    public void refreshPage() throws SQLException, ClassNotFoundException {
        loadTable();
        SaveBtn.setDisable(true);
        WardIdLab.setText("");
        WardNameLab.setText("");

        DegradableCheckBoxFalse();
        RecyclableCheckBoxFalse();
        NonRecyclableCheckBoxFalse();
    }
    public void DegradableCheckBoxFalse(){
        DegradeDailyCheck.setSelected(false);DegradeMondayCheck.setSelected(false);DegradeTueCheck.setSelected(false);DegradeWedCheck.setSelected(false);
        DegradeThurCheck.setSelected(false);DegradeFridCheck.setSelected(false);DegradeSaturCheck.setSelected(false);DegradeSunCheck.setSelected(false);
    }

    public void RecyclableCheckBoxFalse(){
        RecyDailyCheck.setSelected(false);RecyMondayCheck.setSelected(false);RecyTueCheck.setSelected(false);RecyWedCheck.setSelected(false);
        RecyThurCheck.setSelected(false);RecyFridCheck.setSelected(false);RecySaturCheck.setSelected(false);RecySunCheck.setSelected(false);
    }

    public void NonRecyclableCheckBoxFalse(){
        NonRecDailyCheck.setSelected(false);NonRecMondayCheck.setSelected(false);NonRecTueCheck.setSelected(false);NonRecWedCheck.setSelected(false);
        NonRecThurCheck.setSelected(false);NonRecFridCheck.setSelected(false);NonRecSaturCheck.setSelected(false);NonRecSunCheck.setSelected(false);
    }
    public boolean check(){//////////////
        return check;
    }

    public static boolean check=false;/////////////
    public void loadTable() throws SQLException, ClassNotFoundException {
        MunicipalController municipalController = new MunicipalController();
        boolean checked = municipalController.checkShedule();
        munId=municipalController.getMunicipalId();

        System.out.println("checkShedule in loadTable(): " + checked); // Confirm value
        if(checked){

            ObservableList <SheduleTm> sheduleTms= FXCollections.observableArrayList();

            ArrayList<SheduleTm> all = sheduleModel.getAll(municipalController.getMunicipalId());

            for(SheduleTm sheduleTm:all){
                sheduleTms.add(sheduleTm);
            }
            SheduleTable.setItems(sheduleTms);
            check=true;//////////////
        }

        else{
            //WardModel wardModel = new WardModel();
            ArrayList<WardDto> allFromMunicipal = wardBO.getAllFromMunicipal(municipalController.getMunicipalId());

            ArrayList<WardTm> allWards = new ArrayList<>();
            for (WardDto wardDto : allFromMunicipal) {
                allWards.add(new WardTm(wardDto.getWardId(),wardDto.getMunicipalId(),wardDto.getWardName()));
            }
            System.out.println("allWards in loadTable(): " + allWards);

            boolean result = sheduleModel.insertWards(allWards);
            if(result){
                ObservableList <SheduleTm> sheduleTms= FXCollections.observableArrayList();

                ArrayList<SheduleTm> all = sheduleModel.getAll(municipalController.getMunicipalId());

                for(SheduleTm sheduleTm:all){
                    sheduleTms.add(sheduleTm);
                }
                SheduleTable.setItems(sheduleTms);
            }

            System.out.println("insertWards in loadTable(): " + result);
        }
    }

    public void SheduleReportAction(ActionEvent actionEvent) {
        if(munId==null){
            new Alert(Alert.AlertType.ERROR, "Select Municipal First!", ButtonType.OK).show();
            return;
        }
        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/Report/CollectionSheduleReport.jrxml")
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
