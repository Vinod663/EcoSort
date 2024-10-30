package org.example.ecosortsoftware;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/View/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ECOSORT");


        Image image = new Image(getClass().getResourceAsStream("/Images/transparent-recycling-environmental-conservation-reusable-bags-garbage-bin-with-reusable-bag-promotes-recycling65537683c136a9.3982713516999686437914.png"));
        stage.getIcons().add(image);
        //stage.setFullScreen(true);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
