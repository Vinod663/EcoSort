package org.example.ecosortsoftware.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.ecosortsoftware.DTO.DisposalDto;
import org.example.ecosortsoftware.DTO.InventoryDto;
import org.example.ecosortsoftware.Model.DisposalModel;
import org.example.ecosortsoftware.Model.InventoryModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    public TextField deployWasteText;
    public Button deployBtn;
    @FXML
    private Label currentWasteCapacityLab;

    @FXML
    private Label inventoryIdLabel;

    @FXML
    private Button saveBtn;

    @FXML
    private Label statusLab;

    @FXML
    private Label wasteCapacityLab;

    @FXML
    private TextField wasteCapacityText;

    MunicipalController municipalController= new MunicipalController();
    String muniId;
    @FXML

    void SaveBtnAction(ActionEvent event) {

//        System.out.println(muniId);

        String wasteCapacityStr = wasteCapacityText.getText();

        // Check if the field is empty
        if (wasteCapacityStr.isEmpty()) {
            System.out.println("Waste capacity is required.");
            new Alert(Alert.AlertType.ERROR, "Waste capacity is required.!", ButtonType.OK).show();
            return;
        }

        try {
            double wasteCapacity = Double.parseDouble(wasteCapacityStr);

            // Ensure wasteCapacity is non-negative
            if (wasteCapacity >= 0&&Double.parseDouble(currentWasteCapacityLab.getText())<wasteCapacity) {
                double wasteCapcity= Double.parseDouble(wasteCapacityStr);
                InventoryDto inventoryDto = new InventoryDto();
                inventoryDto.setInventoryId(inventoryIdLabel.getText());
                inventoryDto.setWasteAmount(Double.parseDouble(currentWasteCapacityLab.getText()));
                inventoryDto.setStatus(statusLab.getText());
                inventoryDto.setMunicipalId(muniId);
                inventoryDto.setCapacity(Double.parseDouble(wasteCapacityText.getText()));

                boolean isUpdated = inventoryModel.updateInventory(inventoryDto);
                if (isUpdated) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Inventory Updated", ButtonType.OK).show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Inventory Not Updated.Something went wrong!", ButtonType.OK).show();
                }




            } else {
                System.out.println("Waste capacity must be non-negative or greater than current waste capacity in the inventory.");
                new Alert(Alert.AlertType.ERROR, "Waste capacity must be non-negative or greater than current waste capacity in the inventory.!", ButtonType.OK).show();

            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number for waste capacity.");
            new Alert(Alert.AlertType.ERROR, "Please enter a valid number for waste capacity.!", ButtonType.OK).show();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
        //muniId = municipalController.getMunicipalId();

    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadData();
    }
    InventoryModel inventoryModel=new InventoryModel();
    private void loadData() throws SQLException, ClassNotFoundException {
        muniId = municipalController.getMunicipalId();
        System.out.println("Loading Inventory data of "+muniId);

        InventoryDto all = inventoryModel.getAll(muniId);
        System.out.println(all);
        String currentStatus=all.getStatus();
        if(currentStatus.equals("Inactive")){
            statusLab.setStyle("-fx-text-fill: red;");
        }
        else{
            statusLab.setStyle("-fx-text-fill: #0c843c;");
        }
        inventoryIdLabel.setText(all.getInventoryId());
        statusLab.setText(all.getStatus());
        currentWasteCapacityLab.setText(String.valueOf(all.getWasteAmount()));
        wasteCapacityLab.setText(String.valueOf(all.getCapacity()));
        wasteCapacityText.setText(String.valueOf(all.getCapacity()));

    }

    public void DeployBtnAction(ActionEvent actionEvent) {
        String text = deployWasteText.getText();
        if (text.isEmpty()) {
            System.out.println("Deploy Waste capacity is required!");
            new Alert(Alert.AlertType.ERROR, "Deploy Waste capacity is required!", ButtonType.OK).show();
            return;
        }

        try {
            double deployWaste = Double.parseDouble(text);
            double newWasteAmount=Double.parseDouble(currentWasteCapacityLab.getText())-deployWaste;
            String status;
            if(newWasteAmount == Double.parseDouble(wasteCapacityLab.getText())){
                status="Inactive";
            }
            else{
                status="Active";
            }
            // Ensure wasteCapacity is non-negative
            if (deployWaste >= 0&&Double.parseDouble(currentWasteCapacityLab.getText())>=deployWaste) {

                InventoryDto inventoryDto = new InventoryDto();
                inventoryDto.setInventoryId(inventoryIdLabel.getText());
                inventoryDto.setWasteAmount(newWasteAmount);
                inventoryDto.setStatus(status);
                inventoryDto.setMunicipalId(muniId);
                inventoryDto.setCapacity(Double.parseDouble(wasteCapacityLab.getText()));

                boolean isUpdated = inventoryModel.updateInventory(inventoryDto);
                if (isUpdated) {
                    System.out.println("Check");
                    refreshPage();
                    LocalDate currentDate = LocalDate.now();
                    DisposalDto disposalDto=new DisposalDto();
                    DisposalModel disposalModel=new DisposalModel();
                    disposalDto.setDisposalId(disposalModel.getNextDisposalId());
                    disposalDto.setDisposalDate(java.sql.Date.valueOf(currentDate));
                    disposalDto.setWasteAmount(deployWaste);
                    disposalDto.setMunicipalId(muniId);
                    boolean disposalResult = disposalModel.saveDisposal(disposalDto);
                    if (disposalResult) {
                        System.out.println("Disposal successfully saved!");
                    }
                    else {
                        System.out.println("Disposal failed!");
                    }

                    new Alert(Alert.AlertType.INFORMATION, "Waste Deployed", ButtonType.OK).show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Waste Not Deployed.Something went wrong!", ButtonType.OK).show();
                }




            } else {
                System.out.println("Deploy waste capacity must be lower than or equal to current waste amount in the inventory!.");
                new Alert(Alert.AlertType.ERROR, "Deploy waste capacity must be lower than or equal to current waste amount in the inventory!", ButtonType.OK).show();

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Please enter a valid number for deploy waste amount.");
            new Alert(Alert.AlertType.ERROR, "Please enter a valid number for deploy waste amount.!", ButtonType.OK).show();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
