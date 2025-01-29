package org.example.ecosortsoftware.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.ecosortsoftware.dto.ComplaintsDto;
import org.example.ecosortsoftware.dto.Tm.ComplaintsTm;
import org.example.ecosortsoftware.Model.ComplaintModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ComplaintController implements Initializable {

    @FXML
    private Button ResetBtn;

    @FXML
    private Label complaintIdLab;

    @FXML
    private TableColumn<ComplaintsTm, String> complaintIdcol;

    @FXML
    private TableView<ComplaintsTm> complaintTable;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<ComplaintsTm, String> descriptionCol;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private RadioButton notSettledRadio;

    @FXML
    private Button saveBtn;

    @FXML
    private RadioButton settledRadio;

    @FXML
    private TableColumn<ComplaintsTm, String> statusCol;

    @FXML
    private Button updateBtn;

    @FXML
    void DeleteBtnAction(ActionEvent event) {
        try{
            String id=complaintIdLab.getText();

            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if(buttonType.isPresent()&&buttonType.get()==ButtonType.YES){
                boolean result=complaintModel.DeleteComplaint(id);

                if(result){
                    refershPage();
                    new Alert(Alert.AlertType.INFORMATION, "Complaint Deleted Successfully", ButtonType.OK).show();
                }
                else{
                    new Alert(Alert.AlertType.ERROR, "Complaint Deletion Failed Employee Not Found!", ButtonType.OK).show();
                }
            }


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error Complaint Deletion Failed", ButtonType.OK).show();
        }
    }

    @FXML
    void ResetBtnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refershPage();
    }

    @FXML
    void TableClickAction(MouseEvent event) {
        ComplaintsTm complaintsTm = complaintTable.getSelectionModel().getSelectedItem();
        if (complaintsTm != null) {
            saveBtn.setDisable(true);
            updateBtn.setDisable(false);
            deleteBtn.setDisable(false);
            complaintIdLab.setText(complaintsTm.getComplaintId());
            descriptionTextArea.setText(complaintsTm.getDescription());
            if(complaintsTm.getStatus().equals("Settled")){
                settledRadio.setSelected(true);
            }
            else{
                notSettledRadio.setSelected(true);
            }

        }
    }

    @FXML
    void saveBtnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(!settledRadio.isSelected()&&!notSettledRadio.isSelected()){
            new Alert(Alert.AlertType.ERROR, "Select Status before save!", ButtonType.OK).show();
            return;
        }
        if(descriptionTextArea.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Enter Description about complaint before save!", ButtonType.OK).show();
            return;
        }
        ComplaintsDto complaintsDto=new ComplaintsDto();
        complaintsDto.setComplaintId(complaintIdLab.getText());
        if(settledRadio.isSelected()){
            complaintsDto.setStatus("Settled");
        }
        if(notSettledRadio.isSelected()){
            complaintsDto.setStatus("Not-Settled");
        }
        complaintsDto.setDescription(descriptionTextArea.getText());
        complaintsDto.setMunicipalId(munId);

        boolean isSaved = complaintModel.saveComplaint(complaintsDto);
        if (isSaved) {
            refershPage();
            new Alert(Alert.AlertType.INFORMATION, "Complaint Saved", ButtonType.OK).show();

        } else {
            new Alert(Alert.AlertType.ERROR, "Complaint Not Saved.Something went wrong!", ButtonType.OK).show();
        }


    }

    @FXML
    void updateBtnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ComplaintsDto complaintsDto=new ComplaintsDto();
        complaintsDto.setComplaintId(complaintIdLab.getText());
        if(settledRadio.isSelected()){
            complaintsDto.setStatus("Settled");
        }
        if(notSettledRadio.isSelected()){
            complaintsDto.setStatus("Not-Settled");
        }
        complaintsDto.setDescription(descriptionTextArea.getText());
        complaintsDto.setMunicipalId(munId);
        boolean isUpdated = complaintModel.updateComplaint(complaintsDto);
        if (isUpdated) {
            refershPage();
            new Alert(Alert.AlertType.INFORMATION, "Complaint Updated", ButtonType.OK).show();

        } else {
            new Alert(Alert.AlertType.ERROR, "Complaint Not Updated.Something went wrong!", ButtonType.OK).show();
        }
    }
    String munId;
    ComplaintModel complaintModel=new ComplaintModel();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refershPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        complaintIdcol.setCellValueFactory(new PropertyValueFactory<>("complaintId"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        ToggleGroup toggleGroup = new ToggleGroup();
        settledRadio.setToggleGroup(toggleGroup);
        notSettledRadio.setToggleGroup(toggleGroup);
        munId=municipalController.getMunicipalId();
    }

    private void refershPage() throws SQLException, ClassNotFoundException {
        complaintIdLab.setText(String.valueOf(complaintModel.getNextCpId()));
        saveBtn.setDisable(false);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
        descriptionTextArea.setText("");
        settledRadio.setSelected(false);
        notSettledRadio.setSelected(false);
        loadTable();
    }
    MunicipalController municipalController=new MunicipalController();
    public void loadTable() throws SQLException, ClassNotFoundException {
        ObservableList<ComplaintsTm> complaintTms= FXCollections.observableArrayList();

        ArrayList<ComplaintsTm> all = ComplaintModel.getAll(municipalController.getMunicipalId());

        for(ComplaintsTm complaintTm:all){
            complaintTms.add(complaintTm);
        }
        complaintTable.setItems(complaintTms);

    }
}
