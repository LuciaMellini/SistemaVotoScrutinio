package controllers;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import model.Cae;
import model.Sessione;
import model.SistemaVotoScrutinio;
import model.Utente;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AreaCAEController {
	private Cae cae;
	
	@FXML
	private Button btnInserimentoSchedaElettorale;
	
	@FXML
	private Button btnCreaSessione;
	
    @FXML
    private Button btnScrutinio;
    
    @FXML
    private Button btnGetBackCAE;
    
    @FXML
    private Label lblMessage;
    
   
    public void setCae(Cae c) {
    	this.cae = c;
    	btnScrutinio.setVisible(cae.isScrutinatore());
    	btnCreaSessione.setVisible(cae.isConfiguratore());
    }
    
    @FXML
    void handleChoiceSessione(MouseEvent event) {}
    
    @FXML
    void handleBtnInserimentoSchedaElettorale(ActionEvent event) throws IOException{
    	Stage stage = (Stage) btnInserimentoSchedaElettorale.getScene().getWindow();
    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/inserimentoSchedeElettorali.fxml")); 
    	Parent root = fxmlLoader.load();
    	InserimentoSchedeElettoraliController controller = fxmlLoader.getController();
    	controller.setCae(cae);
    	Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
        return;
    }
    
    @FXML
    void handleBtnCreaSessione(ActionEvent event) throws IOException{
    	Stage stage = (Stage) btnCreaSessione.getScene().getWindow();
	    FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/creaSessione.fxml"));
	    Parent root = (Parent) fxmlLoader.load();
	    CreaSessioneController controller = fxmlLoader.getController();
	    controller.setCae(cae);
	       
	    Scene scene = new Scene(root, 770, 620);
	    stage.setTitle("SistemaVotoScrutinio");
	    stage.setScene(scene);
	    stage.show();    	
	    root.requestFocus();
    }

    @FXML
    void handleScrutinio(ActionEvent event) throws IOException{
		Stage stage = (Stage) btnScrutinio.getScene().getWindow();
    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/areaScrutinio.fxml"));
    	Parent root = (Parent) fxmlLoader.load();
        AreaScrutinioController controller = fxmlLoader.getController();
        controller.setCae(cae);
        
    	Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }
   
    @FXML
    void handleGetBackCAE(ActionEvent event) throws IOException{
    	cae.uscita();
    	Stage stage = (Stage) btnGetBackCAE.getScene().getWindow();
    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/sceltaTipoUtente.fxml"));    	
    	Parent root = (Parent) fxmlLoader.load();
        SceltaTipoUtenteController controller = fxmlLoader.getController();
        controller.setUtente(new Utente(cae.getEmail()));
        
    	Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

}