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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.example.ecosortsoftware.bo.BOFactory;
import org.example.ecosortsoftware.bo.InventoryBO;
import org.example.ecosortsoftware.bo.WardBO;
import org.example.ecosortsoftware.db.DBConnection;
import org.example.ecosortsoftware.dto.*;
import org.example.ecosortsoftware.dto.Tm.WasteCollectionTm;
import org.example.ecosortsoftware.Model.*;
import org.example.ecosortsoftware.entity.Inventory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WasteCollectionController implements Initializable {

    InventoryBO inventoryBO= (InventoryBO) BOFactory.getInstance().getBO(BOFactory.BOType.INVENTORY);
    WardBO wardBO= (WardBO) BOFactory.getInstance().getBO(BOFactory.BOType.WARD);

    public Label divisionNameLab;
    public Button wasteReport;
    public Button statsBtn;
    public AnchorPane Anchor;
    @FXML
    private DatePicker CollectionDate;

    @FXML
    private Button DeleteBtn;

    @FXML
    private TextField NonRRecyclableWasteText;

    @FXML
    private Button ResetBtn;

    @FXML
    private Button SaveBtn;

    @FXML
    private Button UpdateBtn;

    @FXML
    private TableColumn<WasteCollectionTm, Double> collectedWasteCol;

    @FXML
    private TableColumn<WasteCollectionTm, String> collectionIdCol;

    @FXML
    private Label collectionIdLab;

    @FXML
    private TableColumn<WasteCollectionTm, String> dateCol;

    @FXML
    private TextField degrableWasteText;

    @FXML
    private TableColumn<WasteCollectionTm, Double> degradableCol;

    @FXML
    private ComboBox<String> divisionIdCombo;

    @FXML
    private Label inventoryIdLab;

    @FXML
    private TableColumn<WasteCollectionTm, Double> nonRecyclableCol;

    @FXML
    private TableColumn<WasteCollectionTm, Double> recyclableCol;

    @FXML
    private TextField recyclableWasteText;

    @FXML
    private TableColumn<WasteCollectionTm, Double> totalWasteCol;

    @FXML
    private ComboBox<String> vehicleIdCombo;

    @FXML
    private TableView<WasteCollectionTm> wasteCollectionTable;

    @FXML
    void DeleteBtnAction(ActionEvent event) {
        WasteCollectionTm selectedItem = wasteCollectionTable.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            new Alert(Alert.AlertType.ERROR, "Select Waste Collection first!", ButtonType.OK).show();
            return;
        }
        try{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.isPresent()&&buttonType.get()==ButtonType.YES){
                String collectionId=collectionIdLab.getText();
                String inventoryId=inventoryIdLab.getText();
                double inventoryCapacity = inventoryBO.getInventoryCapacity(inventoryIdLab.getText());

                InventoryDto inventory = inventoryBO.getAll(muniId);

                InventoryDto all = new InventoryDto(inventory.getInventoryId(),inventory.getWasteAmount(),inventory.getStatus(),
                        inventory.getMunicipalId(),inventory.getCapacity());

                //Inventory all = inventoryBO.getAll(muniId);
                double previousTotalWaste = all.getWasteAmount();
                double deleteNonrecyclable=selectedItem.getNonRecyclableWasteAmount();
                double newTotWaste=previousTotalWaste-deleteNonrecyclable;

                boolean result=wasteCollectionModel.DeleteCollection(collectionId);

                if(result){
//                    refrshPage();
                    System.out.println("Waste Collection Deleted");

//                    boolean res=recyclingModel.DeleteRecycling(collectionId);
                    System.out.println("Recycling section Deleted");
                    InventoryDto inventoryDto = new InventoryDto();
                    inventoryDto.setInventoryId(inventoryId);
                    inventoryDto.setWasteAmount(newTotWaste);
                    inventoryDto.setStatus("Active");
                    inventoryDto.setMunicipalId(muniId);
                    inventoryDto.setCapacity(inventoryCapacity);

                    boolean updateInventory=inventoryBO.update(inventoryDto);

                    if(updateInventory){
                        refrshPage();
                        System.out.println("Inventory updated");
                        new Alert(Alert.AlertType.INFORMATION, "Waste Collection Deleted and Inventory updated successfully. ", ButtonType.OK).show();
                    }
                    else{
                        new Alert(Alert.AlertType.ERROR, "Inventory update failed!", ButtonType.OK).show();
                    }
//
                }
                else{
                    new Alert(Alert.AlertType.ERROR, "WasteCollection Deletion failed!", ButtonType.OK).show();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void ResetBtnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refrshPage();
    }
    RecyclingModel recyclingModel=new RecyclingModel();
    @FXML

    void SaveBtnAction(ActionEvent event) {

        String recyclable=recyclableWasteText.getText();
        String degradable=degrableWasteText.getText();
        String NonRecyclable=NonRRecyclableWasteText.getText();

        if(recyclable.isEmpty()||degradable.isEmpty()||NonRecyclable.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Enter waste Amount for Each Section!", ButtonType.OK).show();
            return;
        }
        if(vehicleIdCombo.getSelectionModel().getSelectedItem()==null||divisionIdCombo.getSelectionModel().getSelectedItem()==null){
            new Alert(Alert.AlertType.ERROR, "Select Vehicle Id and Division Id!", ButtonType.OK).show();
            return;
        }
        try{
            double recyclableWaste= Double.parseDouble(recyclableWasteText.getText());//need to validate
            double nonRecyclableWaste= Double.parseDouble(NonRRecyclableWasteText.getText());
            double degradableWaste= Double.parseDouble(degrableWasteText.getText());
            if(recyclableWaste>=0&&nonRecyclableWaste>=0&&degradableWaste>=0) {
                String status = "Active";

                double collectedWaste = recyclableWaste + nonRecyclableWaste + degradableWaste;
                System.out.println("Collected waste amount:" + collectedWaste);
//                double previousTotalWaste = wasteCollectionModel.getTotalWaste(muniId);
                InventoryDto inventory = inventoryBO.getAll(muniId);

                InventoryDto all = new InventoryDto(inventory.getInventoryId(),inventory.getWasteAmount(),inventory.getStatus(),
                        inventory.getMunicipalId(),inventory.getCapacity());
//                Inventory all = inventoryBO.getAll(muniId);
                double previousTotalWaste = all.getWasteAmount();
                System.out.println("Previous Total Waste amount in Inventory:" + previousTotalWaste);
                double newTotalWaste = previousTotalWaste + nonRecyclableWaste;
                System.out.println("New Total Waste amount in Inventory:" + newTotalWaste);
                double inventoryCapacity = inventoryBO.getInventoryCapacity(inventoryIdLab.getText());
                System.out.println("Inventory Capacity:" + inventoryCapacity);

                if (inventoryCapacity < newTotalWaste) {
                    new Alert(Alert.AlertType.ERROR, "Inventory is full! Before enter this capacity of NonRecyclable waste Please deploy some waste from Inventory!!", ButtonType.OK).show();
                    return;
                }
                if (inventoryCapacity <= newTotalWaste) {
                    status = "Inactive";
                }
                //WasteCollection save
                WasteCollectionDto collectionDto = new WasteCollectionDto();

                LocalDate date = CollectionDate.getValue(); // Gets the LocalDate from DatePicker
                String formattedDate = null;
                if (date != null) {
                    // Format the date directly from LocalDate
                    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    formattedDate = date.format(outputFormatter);
                    System.out.println("Formatted Date: " + formattedDate);
                } else {
                    System.out.println("No date selected.");
                }
                String collId=collectionIdLab.getText();
                collectionDto.setCollectionId(collectionIdLab.getText());
                collectionDto.setVehicleId(vehicleIdCombo.getSelectionModel().getSelectedItem());
                collectionDto.setInventoryId(inventoryIdLab.getText());
                collectionDto.setTotalWasteAmount(newTotalWaste);
                collectionDto.setCollectionDate(formattedDate);
                collectionDto.setDivisionId(divisionIdCombo.getSelectionModel().getSelectedItem());
                collectionDto.setCollectedWasteAmount(collectedWaste);
                collectionDto.setDegradableWasteAmount(degradableWaste);
                collectionDto.setRecyclableWasteAmount(recyclableWaste);
                collectionDto.setNonRecyclableWasteAmount(nonRecyclableWaste);
                collectionDto.setMunicipalId(muniId);

                boolean isUpdated = wasteCollectionModel.saveCollection(collectionDto);
                if (isUpdated) {
//                    refrshPage();
                    System.out.println("Waste Collection Updated");
//                    inventory update
                    InventoryDto inventoryDto = new InventoryDto();
                    inventoryDto.setInventoryId(inventoryIdLab.getText());
                    inventoryDto.setWasteAmount(newTotalWaste);
                    inventoryDto.setStatus(status);
                    inventoryDto.setMunicipalId(muniId);
                    inventoryDto.setCapacity(inventoryCapacity);

                    boolean isInventoryUpdated = inventoryBO.update(inventoryDto);
                    if (isInventoryUpdated) {
//                        refreshPage();
                        System.out.println("Inventory Updated");
                        refrshPage();
                        new Alert(Alert.AlertType.INFORMATION, "WasteCollection and Inventory data Saved", ButtonType.OK).show();
                        if (recyclableWaste > 0) {//Recycling save
                            String recyclingId = recyclingModel.getNextRecyclingId();
                            System.out.println("Recycling Id:" + recyclingId);

                            RecyclingDto recyclingDto = new RecyclingDto();
                            recyclingDto.setRecyclingId(recyclingId);
                            recyclingDto.setInventoryId(inventoryIdLab.getText());
                            recyclingDto.setQuantity(recyclableWaste);
                            recyclingDto.setDate(formattedDate);
                            recyclingDto.setMunicipalId(muniId);
                            recyclingDto.setCollectionId(collId);


                            boolean isRecyclingUpdated = recyclingModel.saveRecycling(recyclingDto);
                            if (isRecyclingUpdated) {
//                                refrshPage();
                                System.out.println("Recycling saved");

//                                new Alert(Alert.AlertType.INFORMATION, "WasteCollection Saved", ButtonType.OK).show();

                            } else {
                                System.out.println("Recycling not saved");
                                new Alert(Alert.AlertType.ERROR, "WasteCollection not Saved! something went wrong!", ButtonType.OK).show();
                            }
                        }

                    } else {
                        System.out.println("Inventory Not Updated");
                        new Alert(Alert.AlertType.ERROR, "WasteCollection and Inventory not Saved Correctly! something went wrong!", ButtonType.OK).show();
                        return;
                    }

                } else {
                    new Alert(Alert.AlertType.ERROR, "WasteCollection not Saved! something went wrong!", ButtonType.OK).show();
                    System.out.println("Waste Collection Not Saved!");
                    return;
                }



            }
            else{
                new Alert(Alert.AlertType.ERROR, "Waste amount of each section must be non-negative!", ButtonType.OK).show();
            }
        }
        catch(NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Enter valid number for each waste amount!", ButtonType.OK).show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void TableOnClickAction(MouseEvent event) {
        WasteCollectionTm selectedItem = wasteCollectionTable.getSelectionModel().getSelectedItem();
        if(selectedItem!=null) {
            collectionIdLab.setText(selectedItem.getCollectionId());
            vehicleIdCombo.getSelectionModel().select(selectedItem.getVehicleId());
            divisionIdCombo.getSelectionModel().select(selectedItem.getDivisionId());
            CollectionDate.setValue(LocalDate.parse(selectedItem.getCollectionDate()));
            recyclableWasteText.setText(String.valueOf(selectedItem.getRecyclableWasteAmount()));
            degrableWasteText.setText(String.valueOf(selectedItem.getDegradableWasteAmount()));
            NonRRecyclableWasteText.setText(String.valueOf(selectedItem.getNonRecyclableWasteAmount()));

            DeleteBtn.setDisable(false);
            SaveBtn.setDisable(true);
            UpdateBtn.setDisable(false);
        }
    }

    @FXML
    void UpdateBtnAction(ActionEvent event) {
        String recyclable=recyclableWasteText.getText();
        String degradable=degrableWasteText.getText();
        String NonRecyclable=NonRRecyclableWasteText.getText();

        if(recyclable.isEmpty()||degradable.isEmpty()||NonRecyclable.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Enter waste Amount for Each Section!", ButtonType.OK).show();
            return;
        }
        if(vehicleIdCombo.getSelectionModel().getSelectedItem()==null||divisionIdCombo.getSelectionModel().getSelectedItem()==null){
            new Alert(Alert.AlertType.ERROR, "Select Vehicle Id and Division Id!", ButtonType.OK).show();
            return;
        }
        try{
            double recyclableWaste= Double.parseDouble(recyclableWasteText.getText());//need to validate
            double nonRecyclableWaste= Double.parseDouble(NonRRecyclableWasteText.getText());
            double degradableWaste= Double.parseDouble(degrableWasteText.getText());
            if(recyclableWaste>=0&&nonRecyclableWaste>=0&&degradableWaste>=0) {
                String status = "Active";////////////////

                WasteCollectionTm selectedItem = wasteCollectionTable.getSelectionModel().getSelectedItem();
                double prevTotalwaste=selectedItem.getTotalWasteAmount();
                double prevNonrecyclable=selectedItem.getNonRecyclableWasteAmount();

                double collectedWaste = recyclableWaste + nonRecyclableWaste + degradableWaste;
                System.out.println("Collected waste amount:" + collectedWaste);
                double previousTotalWaste = wasteCollectionModel.getTotalWaste(muniId);
                System.out.println("Previous Total Waste amount in Inventory:" + previousTotalWaste);

                InventoryDto inventory = inventoryBO.getAll(muniId);

                InventoryDto all = new InventoryDto(inventory.getInventoryId(),inventory.getWasteAmount(),inventory.getStatus(),
                        inventory.getMunicipalId(),inventory.getCapacity());
//                Inventory all = inventoryBO.getAll(muniId);

                double newTotalWaste;
                if(nonRecyclableWaste==selectedItem.getNonRecyclableWasteAmount()){
//                    newTotalWaste=wasteCollectionModel.getTotalWaste(muniId);
                    newTotalWaste= all.getWasteAmount();
                }
                else{
//                    newTotalWaste = wasteCollectionModel.getTotalWaste(muniId)-prevNonrecyclable+nonRecyclableWaste;////////
                    newTotalWaste = all.getWasteAmount()-prevNonrecyclable+nonRecyclableWaste;
                }

                System.out.println("New Total Waste amount in Inventory:" + newTotalWaste);
                double inventoryCapacity = inventoryBO.getInventoryCapacity(inventoryIdLab.getText());
                System.out.println("Inventory Capacity:" + inventoryCapacity);

                if (inventoryCapacity < newTotalWaste) {
                    new Alert(Alert.AlertType.ERROR, "Inventory is full! Before enter this capacity of NonRecyclable waste Please deploy some waste from Inventory!!", ButtonType.OK).show();
                    return;
                }
                if (inventoryCapacity <= newTotalWaste) {
                    status = "Inactive";
                }
                //WasteCollection save
                WasteCollectionDto collectionDto = new WasteCollectionDto();

                LocalDate date = CollectionDate.getValue(); // Gets the LocalDate from DatePicker
                String formattedDate = null;
                if (date != null) {
                    // Format the date directly from LocalDate
                    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    formattedDate = date.format(outputFormatter);
                    System.out.println("Formatted Date: " + formattedDate);
                } else {
                    System.out.println("No date selected.");
                }

                collectionDto.setCollectionId(collectionIdLab.getText());
                collectionDto.setVehicleId(vehicleIdCombo.getSelectionModel().getSelectedItem());
                collectionDto.setInventoryId(inventoryIdLab.getText());
                collectionDto.setTotalWasteAmount(newTotalWaste);
                collectionDto.setCollectionDate(formattedDate);
                collectionDto.setDivisionId(divisionIdCombo.getSelectionModel().getSelectedItem());
                collectionDto.setCollectedWasteAmount(collectedWaste);
                collectionDto.setDegradableWasteAmount(degradableWaste);
                collectionDto.setRecyclableWasteAmount(recyclableWaste);
                collectionDto.setNonRecyclableWasteAmount(nonRecyclableWaste);
                collectionDto.setMunicipalId(muniId);

                boolean isUpdated = wasteCollectionModel.updateCollection(collectionDto);
                if (isUpdated) {
//                    refrshPage();
                    System.out.println("Waste Collection Updated");
//                    inventory update
                    InventoryDto inventoryDto = new InventoryDto();
                    inventoryDto.setInventoryId(inventoryIdLab.getText());
                    inventoryDto.setWasteAmount(newTotalWaste);
                    inventoryDto.setStatus(status);
                    inventoryDto.setMunicipalId(muniId);
                    inventoryDto.setCapacity(inventoryCapacity);

                    boolean isInventoryUpdated = inventoryBO.update(inventoryDto);
                    if (isInventoryUpdated) {
//                        refreshPage();
                        System.out.println("Inventory Updated");
//                       boolean check=recyclingModel.checkData();
                        refrshPage();
                        new Alert(Alert.AlertType.INFORMATION, "WasteCollection and Inventory Updated", ButtonType.OK).show();
//                        if (recyclableWaste > 0) {//Recycling save

                            String recyclingId = recyclingModel.getNextRecyclingId();
                            System.out.println("Recycling Id:" + recyclingId);

                            RecyclingDto recyclingDto = new RecyclingDto();
                            recyclingDto.setRecyclingId(recyclingId);
                            recyclingDto.setInventoryId(inventoryIdLab.getText());
                            recyclingDto.setQuantity(recyclableWaste);
                            recyclingDto.setDate(formattedDate);
                            recyclingDto.setMunicipalId(muniId);
                            recyclingDto.setCollectionId(selectedItem.getCollectionId());


                            boolean isRecyclingUpdated = recyclingModel.updateRecycling(recyclingDto);
                            if (isRecyclingUpdated) {
//                                refrshPage();
                                System.out.println("Recycling saved");
//                                new Alert(Alert.AlertType.INFORMATION, "WasteCollection Updated", ButtonType.OK).show();

                            } else {
                                System.out.println("Recycling not saved");
//                                new Alert(Alert.AlertType.ERROR, "WasteCollection not Updated! something went wrong!", ButtonType.OK).show();
                            }
//                        }

                    } else {
                        new Alert(Alert.AlertType.ERROR, "WasteCollection and Inventory not Updated Correctly! something went wrong!", ButtonType.OK).show();
                        System.out.println("Inventory Not Updated!");
                        return;
                    }

                } else {
                    System.out.println("Waste Collection Not Updated");
                    new Alert(Alert.AlertType.ERROR, "WasteCollection not Updated Correctly! something went wrong!", ButtonType.OK).show();
                    return;
                }



            }
            else{
                new Alert(Alert.AlertType.ERROR, "Waste amount of each section must be non-negative!", ButtonType.OK).show();
            }
        }
        catch(NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Enter valid number for each waste amount!", ButtonType.OK).show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    String muniId;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        collectionIdCol.setCellValueFactory(new PropertyValueFactory<>("collectionId"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("collectionDate"));
        totalWasteCol.setCellValueFactory(new PropertyValueFactory<>("totalWasteAmount"));
        recyclableCol.setCellValueFactory(new PropertyValueFactory<>("recyclableWasteAmount"));
        degradableCol.setCellValueFactory(new PropertyValueFactory<>("degradableWasteAmount"));
        nonRecyclableCol.setCellValueFactory(new PropertyValueFactory<>("nonRecyclableWasteAmount"));
        collectedWasteCol.setCellValueFactory(new PropertyValueFactory<>("collectedWasteAmount"));

        MunicipalController municipalController = new MunicipalController();
        String municipalId = municipalController.getMunicipalId();
        muniId = municipalId;
        System.out.println("Municipal Id: "+municipalId);

        try {
            refrshPage();
            loadVehicleIds(municipalId);
            loadDivisionIds(municipalId);
            loadInventoryId(municipalId);
            loadTable(municipalId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    WasteCollectionModel wasteCollectionModel = new WasteCollectionModel();
    private void refrshPage() throws SQLException, ClassNotFoundException {
        collectionIdLab.setText(String.valueOf(wasteCollectionModel.getNextCollectionId()));

        vehicleIdCombo.getItems().clear();
        loadVehicleIds(muniId);
        divisionIdCombo.getItems().clear();
        loadDivisionIds(muniId);
        recyclableWasteText.clear();
        NonRRecyclableWasteText.clear();
        degrableWasteText.clear();
        CollectionDate.setValue(LocalDate.now());
        divisionNameLab.setText("");

        DeleteBtn.setDisable(true);
        ResetBtn.setDisable(false);
        SaveBtn.setDisable(false);
        UpdateBtn.setDisable(true);
        loadTable(muniId);
    }

    public void loadTable(String municipalId) throws ClassNotFoundException, SQLException {
        ObservableList <WasteCollectionTm> collectionTms= FXCollections.observableArrayList();

        ArrayList<WasteCollectionTm> all = wasteCollectionModel.getAll(municipalId);

        for(WasteCollectionTm collectionTm:all){
            collectionTms.add(collectionTm);
        }
        wasteCollectionTable.setItems(collectionTms);
    }


    private void loadInventoryId(String municipalId) throws SQLException, ClassNotFoundException {
        InventoryDto inventoryDto= inventoryBO.getAll(municipalId);

        if (inventoryDto!=null){
            inventoryIdLab.setText(inventoryDto.getInventoryId());
        }
    }

    private void loadDivisionIds(String municipalId) throws SQLException, ClassNotFoundException {
        System.out.println("Loading Division Ids of "+municipalId);

        ArrayList<String> DivisionIds = WasteCollectionModel.getAllDevisionIds(municipalId);
        ObservableList<String> observableDiv = FXCollections.observableArrayList(DivisionIds);
        divisionIdCombo.setItems(observableDiv);
    }

    private void loadVehicleIds(String municipalId) throws SQLException, ClassNotFoundException {
        System.out.println("Loading Vehicle Ids of "+municipalId);

        ArrayList<String> VehicleIds = WasteCollectionModel.getAllVehicleIds(municipalId);
        ObservableList<String> observableVeh = FXCollections.observableArrayList(VehicleIds);
        vehicleIdCombo.setItems(observableVeh);
    }
    //WardModel wardModel=new WardModel();
    public void divisionIdComboAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedId = divisionIdCombo.getSelectionModel().getSelectedItem();
        WardDto wardDto= wardBO.FindById(selectedId);

        if (wardDto!=null){
            divisionNameLab.setText(wardDto.getWardName());
        }
    }


    public void wasteReportAction(ActionEvent actionEvent) {
        if(muniId==null){
            new Alert(Alert.AlertType.ERROR, "Select Municipal First!", ButtonType.OK).show();
            return;
        }
        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/Report/WasteCollectionReport.jrxml")
            );
            Connection connection= DBConnection.getInstance().getConnection();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("P_Date", LocalDate.now().toString());
            parameters.put("P_Municipal_Id",muniId);
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

    public void StatsAction(ActionEvent actionEvent) {
        try {
            Anchor.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/View/Statics.fxml"));

            load.prefWidthProperty().bind(Anchor.widthProperty());
            load.prefHeightProperty().bind(Anchor.heightProperty());

            Anchor.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }
}
