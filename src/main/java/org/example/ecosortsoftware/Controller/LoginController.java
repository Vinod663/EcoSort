package org.example.ecosortsoftware.Controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.example.ecosortsoftware.Model.AdminModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    public AnchorPane ancLogin;
    @FXML
    private Button LogInButton;

    @FXML
    private PasswordField PswFieldId;

    @FXML
    private Hyperlink SignUpHiperLink;

    @FXML
    private TextField UserNameTxtId;

    AdminModel loginModel = new AdminModel();
    @FXML
    void LoginAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String EnteredUsername = UserNameTxtId.getText();
        String EnteredPassword = PswFieldId.getText();

//        if (loginModel.authenticate(EnteredUsername, EnteredPassword)) {//////temporary........*************
            System.out.println("Login successful!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
            AnchorPane newPane = loader.load();

// Set anchors for the new pane
            AnchorPane.setTopAnchor(newPane, 0.0);
            AnchorPane.setRightAnchor(newPane, 0.0);
            AnchorPane.setBottomAnchor(newPane, 0.0);
            AnchorPane.setLeftAnchor(newPane, 0.0);

            newPane.setTranslateX(ancLogin.getWidth());/////////////////////
            ancLogin.getChildren().setAll(newPane);

            Platform.runLater(() -> {////////////////////////////
                // Create a timeline for the transition
                Timeline timeline = new Timeline();
                KeyValue keyValue = new KeyValue(newPane.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
                timeline.getKeyFrames().add(keyFrame);
                timeline.setOnFinished(event1 ->{
                    ancLogin.getChildren().clear(); // Clear the previous contents
                    ancLogin.getChildren().add(newPane); // Ensure newPane is visible after the animation
                });
                timeline.play();///////////////////////////
            });


//            ancLogin.getChildren().clear();
//            AnchorPane loginPane = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
//            ancLogin.getChildren().add(loginPane);

//        } //Temporary..................******************

//        else {// Temporary...........................*************
//
//            if(loginModel.authenticateUsername(EnteredUsername)) {
//                UserNameTxtId.setStyle("-fx-text-box-border: green; -fx-text-inner-color: green;");
//                PswFieldId.setStyle("-fx-text-box-border: red; -fx-text-inner-color: red;");
//            }
//
//            else{
//
//                PswFieldId.setStyle("-fx-text-box-border: red; -fx-text-inner-color: red;");
//                UserNameTxtId.setStyle("-fx-text-box-border: red; -fx-text-inner-color: red;");
//            }
//
////            if (loginModel.authenticatePsw(EnteredPassword)&&loginModel.authenticateUsername(EnteredUsername)) {// not Temporary
////
////                PswFieldId.setStyle("-fx-text-box-border: green; -fx-text-inner-color: green;");
////                UserNameTxtId.setStyle("-fx-text-box-border: green; -fx-text-inner-color: green;");
////            }
//
//
//        }
    }


    @FXML
    void SignUpMouseClickAction(MouseEvent event) throws SQLException, ClassNotFoundException {


    }

}
