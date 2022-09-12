package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BenvenutoController {

    @FXML
    private Button btnAccediCIE;

    @FXML
    private Button btnAccediSPID;

    
    @FXML
    void handleAccediCIE(ActionEvent event) throws IOException{
    	Stage stage = (Stage) btnAccediCIE.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/accessoSPIDCIE.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

    @FXML
    void handleAccediSPID(ActionEvent event) throws IOException{
    	Stage stage = (Stage) btnAccediSPID.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/accessoSPIDCIE.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }
    
}
