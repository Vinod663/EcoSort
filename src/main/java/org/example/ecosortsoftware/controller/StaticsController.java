package org.example.ecosortsoftware.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.example.ecosortsoftware.db.DBConnection;
import org.example.ecosortsoftware.dto.WasteCollectionDto;
import org.example.ecosortsoftware.Model.WasteCollectionModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class StaticsController implements Initializable {

    public Button logWasteBtn;
    public AnchorPane Home;
    @FXML
    private LineChart<String, Number> chart;

    @FXML
    private Button recycleReportBtn;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    void recycleReportAction(ActionEvent event) {
        if(munId==null){
            new Alert(Alert.AlertType.ERROR, "Select Municipal First!", ButtonType.OK).show();
            return;
        }
        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/Report/RecyclingReport.jrxml")
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
    String munId;
    MunicipalController municipalController=new MunicipalController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        munId=municipalController.getMunicipalId();
        System.out.println(munId);

        try {
            loadStats();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
        WasteCollectionDto statics=new WasteCollectionDto();
    private void loadStats() throws SQLException, ClassNotFoundException {
        XYChart.Series<String, Number> recycling = new XYChart.Series<>();
        recycling.setName("Recyclable Waste");
        XYChart.Series<String, Number> degrade = new XYChart.Series<>();
        degrade.setName("Degradable Waste");
        XYChart.Series<String, Number> NonRecy = new XYChart.Series<>();
        NonRecy.setName("Non-Recyclable Waste");


        // Get data from the model
        ArrayList<WasteCollectionDto> stats = WasteCollectionModel.GetData(munId);
        System.out.println(stats);

        // Add data points to the series
        for (WasteCollectionDto dto : stats) {
//            recycling.getData().add(new XYChart.Data<>(dto.getCollectionDate(), dto.getRecyclableWasteAmount()));
//            degrade.getData().add(new XYChart.Data<>(dto.getCollectionDate(), dto.getDegradableWasteAmount()));
//            NonRecy.getData().add(new XYChart.Data<>(dto.getCollectionDate(), dto.getNonRecyclableWasteAmount()));
            recycling.getData().add(new XYChart.Data<>(dto.getCollectionId()+" ("+dto.getCollectionDate()+")", dto.getRecyclableWasteAmount()));
            degrade.getData().add(new XYChart.Data<>(dto.getCollectionId()+" ("+dto.getCollectionDate()+")", dto.getDegradableWasteAmount()));
            NonRecy.getData().add(new XYChart.Data<>(dto.getCollectionId()+" ("+dto.getCollectionDate()+")", dto.getNonRecyclableWasteAmount()));
        }

        // Add the series to the chart
        chart.getData().add(recycling);
        chart.getData().add(degrade);
        chart.getData().add(NonRecy);
    }

    public void LogWasteAction(ActionEvent actionEvent) {
        try {
            Home.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/View/WasteCollection.fxml"));

            load.prefWidthProperty().bind(Home.widthProperty());
            load.prefHeightProperty().bind(Home.heightProperty());

            Home.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }
}
