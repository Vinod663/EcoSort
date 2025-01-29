package org.example.ecosortsoftware.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.ecosortsoftware.bo.BOFactory;
import org.example.ecosortsoftware.bo.EmployeeBO;
import org.example.ecosortsoftware.bo.MunicipalBO;
import org.example.ecosortsoftware.dto.MunicipalDto;
import org.example.ecosortsoftware.view.tdm.MunicipalTm;
import org.example.ecosortsoftware.Model.SheduleModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MunicipalController implements Initializable {

    MunicipalBO municipalBO= (MunicipalBO) BOFactory.getInstance().getBO(BOFactory.BOType.MUNICIPAL);

    public TableColumn<MunicipalTm, String> MunIdCol;
    public TableColumn<MunicipalTm, String> MunNameCol;
    public AnchorPane HomeAnchor;
    @FXML
    private Button LogWasteBtn;

    @FXML
    private TableView<MunicipalTm> MunicipalTable;

    @FXML
    private Button SheduleBtn;

    @FXML
    private Button TrackInventoryBtn;

    @FXML
    void LogWasteBtnAction(ActionEvent event) {
        MunicipalTm selectedMunicipal = MunicipalTable.getSelectionModel().getSelectedItem();
        if (selectedMunicipal == null) {
            new Alert(Alert.AlertType.ERROR, "Select Municipal First!", ButtonType.OK).show();
            return;
        }

        navigateTo("/View/WasteCollection.fxml");
    }

    @FXML
    void OnClickTable(MouseEvent event) {
        MunicipalTm selectedMunicipal = MunicipalTable.getSelectionModel().getSelectedItem();
        munId=selectedMunicipal.getMunicipalId();
    }

    static boolean checkShedule;
    static String munId;
//    EmployeeDto employeeDto=new EmployeeDto();
    @FXML
    void SheduleBtnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        MunicipalTm selectedMunicipal = MunicipalTable.getSelectionModel().getSelectedItem();
        if (selectedMunicipal == null) {
            new Alert(Alert.AlertType.ERROR, "Select Municipal First!", ButtonType.OK).show();
            return;
        }
//         munId=selectedMunicipal.getMunicipalId();
        //employeeDto.setDivisionId(munId);

        //System.out.println(munId);

        SheduleModel sheduleModel = new SheduleModel();
        checkShedule = sheduleModel.checkMunicipalData(munId);
        System.out.println("checkShedule after checkMunicipalData call: " + checkShedule);
        navigateTo("/View/ShedulePage.fxml");

    }
    public boolean checkShedule() {
        return checkShedule;
    }
    public String getMunicipalId() {
        return munId;
    }

    @FXML
    void TrackInventoryBtnAction(ActionEvent event) {
        MunicipalTm selectedMunicipal = MunicipalTable.getSelectionModel().getSelectedItem();
        if (selectedMunicipal == null) {
            new Alert(Alert.AlertType.ERROR, "Select Municipal First!", ButtonType.OK).show();
            return;
        }

        navigateTo("/View/Inventory.fxml");
    }

    public void loadTable() throws SQLException, ClassNotFoundException {

        ObservableList<MunicipalTm> municipalTms= FXCollections.observableArrayList();

        ArrayList<MunicipalDto> municipalDtos= municipalBO.getAll();
        for(MunicipalDto municipalDto : municipalDtos){
            MunicipalTm municipalTm= new MunicipalTm(
                    municipalDto.getMunicipalId(),
                    municipalDto.getMunicipalName()
            );
            municipalTms.add(municipalTm);
        }
        MunicipalTable.setItems(municipalTms);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MunIdCol.setCellValueFactory(new PropertyValueFactory<>("municipalId"));
        MunNameCol.setCellValueFactory(new PropertyValueFactory<>("municipalName"));
        try {
            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        munId=null;

    }

    public void navigateTo(String fxmlPath) {
        try {
            HomeAnchor.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

//  -------- Loaded anchor edges are bound to the content anchor --------
//      (1) Bind the loaded FXML to all edges of the content anchorPane
            load.prefWidthProperty().bind(HomeAnchor.widthProperty());
            load.prefHeightProperty().bind(HomeAnchor.heightProperty());

//      (2) Bind the loaded FXML to all edges of the AnchorPane
//            AnchorPane.setTopAnchor(load, 0.0);
//            AnchorPane.setRightAnchor(load, 0.0);
//            AnchorPane.setBottomAnchor(load, 0.0);
//            AnchorPane.setLeftAnchor(load, 0.0);

            HomeAnchor.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }
}
