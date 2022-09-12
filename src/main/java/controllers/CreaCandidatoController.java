package controllers;

import java.io.IOException;
import java.util.Objects;

import database.SistemaVotoScrutinioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Cae;
import model.Candidato;
import model.SistemaVotoScrutinio;

public class CreaCandidatoController {
	private Cae cae;

    @FXML
    private Button btnCreaCandidato;

    @FXML
    private Button btnGetBack;

    @FXML
    private TextField lblCodiceFiscale;

    @FXML
    private TextField lblCognome;

    @FXML
    private TextField lblNome;
    
    @FXML
    private Label lblMessage;
    
    public void setCae(Cae c) {
    	this.cae = c;
    }

    @FXML
    void handleNome(ActionEvent event) {}

    @FXML
    void handleCodiceFiscale(ActionEvent event) {

    }

    @FXML
    void handleCognome(ActionEvent event) {}

    @FXML
    void handleGetBack(ActionEvent event) throws IOException{
    	Stage stage = (Stage) btnGetBack.getScene().getWindow();
    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/creaSchedaElettorale.fxml"));    	
    	Parent root = (Parent) fxmlLoader.load();
    	CreaSchedaElettoraleController controller = fxmlLoader.getController();
        controller.setCae(cae);
        
    	Scene scene = new Scene(root, 770, 620);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

   
    
    @FXML
    void handleBtnCreaCandidato(ActionEvent event) throws IOException{
    	String nome = lblNome.getText();
    	String cognome = lblCognome.getText();
    	String codiceFiscale = lblCodiceFiscale.getText();
    	if(!Objects.isNull(nome) && !nome.isEmpty() && !nome.trim().isEmpty()) {
    		if(!Objects.isNull(cognome) && !cognome.isEmpty() && !cognome.trim().isEmpty()) {
    			if(!Objects.isNull(codiceFiscale) && !codiceFiscale.isEmpty() && !codiceFiscale.trim().isEmpty()) {
    				if(!codiceFiscale.matches("[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]")) {
    					(new Candidato(codiceFiscale, nome, cognome)).crea();
                		
                		Stage stage = (Stage) btnCreaCandidato.getScene().getWindow();
                    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/creaSchedaElettorale.fxml"));    	
                    	Parent root = (Parent) fxmlLoader.load();
                    	CreaSchedaElettoraleController controller = fxmlLoader.getController();
                        controller.setCae(cae);
                        
                    	Scene scene = new Scene(root, 770, 620);
                        stage.setTitle("SistemaVotoScrutinio");
                        stage.setScene(scene);
                        stage.show();
                        root.requestFocus();
    				}else {
    					lblMessage.setVisible(true);
                		lblMessage.setText("Il codice fiscale inserito non è valido");
    				}
            	}else {
            		lblMessage.setVisible(true);
            		lblMessage.setText("Inserire codice fiscale");
            	}
        	}else {
        		lblMessage.setVisible(true);
        		lblMessage.setText("Inserire cognome");
        	}
    	}else {
    		lblMessage.setVisible(true);
    		lblMessage.setText("Inserire nome");
    	}
    }

}
