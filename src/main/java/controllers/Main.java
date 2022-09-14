package controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import model.Cae;
import model.Sessione;
import model.Elettore;
import model.SistemaVotoScrutinio;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/benvenuto.fxml"));
        Parent root = fxmlLoader.load();
        BenvenutoController controller = fxmlLoader.getController();
        //controller.setCae(new Cae("prova@prova.com"));
        primaryStage.setTitle("SistemaVotoScrutinio");
        primaryStage.setScene(new Scene(root, 570, 420));
        primaryStage.show();
        root.requestFocus();
    }


    public static void main(String[] args) {
    	SistemaVotoScrutinio.getIstanza().log("Apertura sistema");
    	launch(args);
    }
}
