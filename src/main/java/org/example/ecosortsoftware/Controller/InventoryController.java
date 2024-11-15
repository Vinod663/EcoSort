package org.example.ecosortsoftware.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.ecosortsoftware.DTO.InventoryDto;
import org.example.ecosortsoftware.Model.InventoryModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

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
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number for waste capacity.");
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

        inventoryIdLabel.setText(all.getInventoryId());
        statusLab.setText(all.getStatus());
        currentWasteCapacityLab.setText(String.valueOf(all.getWasteAmount()));
        wasteCapacityLab.setText(String.valueOf(all.getCapacity()));
        wasteCapacityText.setText(String.valueOf(all.getCapacity()));

    }
}
