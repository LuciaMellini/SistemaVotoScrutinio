package controllers;

import java.io.IOException;
import model.Cae;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AutenticazioneCAEController {
	private Cae cae;
	
    @FXML
    private Button btnOK;
    
    @FXML
    private Button btnGetBack;
    
    
    
    @FXML
    private TextField lblEmail;
    
    @FXML
    private PasswordField lblPassword;
    
    @FXML
    private Label lblMessage;

    public void setCAE(Cae c) {
    	this.cae = c;
    }

    @FXML
    void handleEmail(ActionEvent event) {
    	
    }

    @FXML
    void handlePassword(ActionEvent event) {

    }
    
    int i=0;
    
    @FXML
    void handleOK(ActionEvent event) throws Exception{
    	String password=lblPassword.getText();
    	
    	Stage stage = (Stage) btnOK.getScene().getWindow();
		FXMLLoader fxmlLoader;
    	if (i<3 && !cae.accesso(password)) {
    		lblMessage.setVisible(true);
    		lblMessage.setText("Le credenziali non sono corrette");
    		i++;
    		return;
    	}else {
    		Parent root;
    		if(i==3) {    			
				fxmlLoader= new FXMLLoader(getClass().getResource("/views/benvenuto.fxml"));
				root = (Parent) fxmlLoader.load();
    		}else {
		        fxmlLoader = new FXMLLoader(getClass().getResource("/views/areaCAE.fxml"));
		        root = (Parent) fxmlLoader.load();
    	        AreaCAEController controller = fxmlLoader.getController();
    	        controller.setCae(cae);  
    	    } 
    		Scene scene = new Scene(root, 570, 420);
    	    stage.setTitle("SistemaVotoScrutinio");
    	    stage.setScene(scene);
    	    stage.show();
    	    root.requestFocus();
    	}		
    }
    
    @FXML
    void handleGetBack(ActionEvent event) throws IOException{
    	cae.uscita();
    	Stage stage = (Stage) btnGetBack.getScene().getWindow();
    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/sceltaTipoUtente.fxml"));    	
    	Parent root = (Parent) fxmlLoader.load();
        SceltaTipoUtenteController controller = fxmlLoader.getController();
        controller.setUtente(cae);
        
    	Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

}