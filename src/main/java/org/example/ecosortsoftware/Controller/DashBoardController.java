package org.example.ecosortsoftware.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.ecosortsoftware.DTO.MunicipalDto;
import org.example.ecosortsoftware.DTO.Tm.MunicipalTm;
import org.example.ecosortsoftware.Model.MunicipalModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    public AnchorPane DashBoardAnc;
    public AnchorPane contentAnchorPane;
    public AnchorPane Content;
    public Button HomeBtn;
    @FXML
    private Button LogOutBtn;

    @FXML
    void LogoutAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
        AnchorPane newPane = loader.load();
        newPane.prefWidthProperty().bind(DashBoardAnc.widthProperty());
        newPane.prefHeightProperty().bind(DashBoardAnc.heightProperty());

        // Set anchors to make the newPane fill the mainAnchorPane
        AnchorPane.setTopAnchor(newPane, 0.0);
        AnchorPane.setRightAnchor(newPane, 0.0);
        AnchorPane.setBottomAnchor(newPane, 0.0);
        AnchorPane.setLeftAnchor(newPane, 0.0);

        // Clear the current pane (if needed) and add the new one
        DashBoardAnc.getChildren().setAll(newPane);
    }

    public void OnClickTable(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/View/MunicipalView.fxml");
    }

    public void navigateTo(String fxmlPath) {
        try {
            Content.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

//  -------- Loaded anchor edges are bound to the content anchor --------
//      (1) Bind the loaded FXML to all edges of the content anchorPane
            load.prefWidthProperty().bind(Content.widthProperty());
            load.prefHeightProperty().bind(Content.heightProperty());

//      (2) Bind the loaded FXML to all edges of the AnchorPane
//            AnchorPane.setTopAnchor(load, 0.0);
//            AnchorPane.setRightAnchor(load, 0.0);
//            AnchorPane.setBottomAnchor(load, 0.0);
//            AnchorPane.setLeftAnchor(load, 0.0);

            Content.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    public void HomeBtnAction(ActionEvent actionEvent) {
        navigateTo("/View/MunicipalView.fxml");
    }
}
