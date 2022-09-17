package controllers;

import java.io.IOException;
import model.Cae;
import model.Elettore;
import model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SceltaTipoUtenteController {
	private Utente utente;
	
    @FXML
    private Button btnAccediCAE;

    @FXML
    private Button btnAccediElettore;
    
    @FXML
    private Button btnGetBack;

    public void setUtente(Utente u) {
    	this.utente = u;
    }

    @FXML
    void accessoCAE(ActionEvent event) throws IOException{
    	Stage stage = (Stage) btnAccediCAE.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/autenticazioneCAE.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AutenticazioneCAEController controller = fxmlLoader.getController();
        Cae cae = new Cae(utente);
        controller.setCAE(cae);
        
        Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

    @FXML
    void accessoElettore(ActionEvent event) throws IOException{
    	Stage stage = (Stage) btnAccediElettore.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/areaElettore.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AreaElettoreController controller = fxmlLoader.getController();
        Elettore elettore = new Elettore(utente);
        controller.setUtente(elettore);
        
        Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

    
    @FXML
    void handleGetBack(ActionEvent event) throws IOException{
    	utente.uscita();
    	Stage stage = (Stage) btnGetBack.getScene().getWindow();
    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/benvenuto.fxml"));    	
    	Parent root = (Parent) fxmlLoader.load();
        BenvenutoController controller = fxmlLoader.getController();
        
    	Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }
}