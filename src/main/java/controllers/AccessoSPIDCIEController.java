package controllers;

import java.io.IOException;
import java.util.regex.Pattern;

import model.Elettore;
import model.Utente;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AccessoSPIDCIEController {
	
    @FXML
    private Button btnOK;

    @FXML
    private TextField lblEmail;
    
    @FXML
    private PasswordField lblPassword;
    
    @FXML
    private Label lblMessage;

    @FXML
    public void initialize() {
    	btnOK.setOnAction(new EventHandler() {
    		
    		int i=0;
			@Override
			public void handle(Event event) {
    			i++;
    			
    			String email=lblEmail.getText();
    	    	String password=lblPassword.getText();
    	    	
    	    	boolean b=Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$").matcher(email).matches(); 
    	    	if(b==false) {
    	    		lblMessage.setVisible(true);
    	    		lblMessage.setText("Formato email non valido");
    	    		return;
    	    	}
    	    	Utente u=new Utente(email);
    	    	
    	    	Stage stage = (Stage) btnOK.getScene().getWindow();
    			FXMLLoader fxmlLoader;
    			Parent root;
    	    	if (i<3 && (!u.accesso(password))) {
    	    		lblMessage.setVisible(true);
    	    		lblMessage.setText("Le credenziali non sono corrette");
    	    		i++;
    	    		return;
    	    	}else {
    	    		try {
    	    			if(i==3) {    			
	    					fxmlLoader= new FXMLLoader(getClass().getResource("/views/credenzialiErrate.fxml"));
	    		            root = (Parent) fxmlLoader.load();
	    				
	    	    		}else {
	    					if(u.isCae()) {
	    			            fxmlLoader = new FXMLLoader(getClass().getResource("/views/sceltaTipoUtente.fxml"));
	    			            root = (Parent) fxmlLoader.load();
	    			            SceltaTipoUtenteController controller = fxmlLoader.getController();
	    			            controller.setUtente(u);
	    			          
	    					}else {
	    			            fxmlLoader = new FXMLLoader(getClass().getResource("/views/areaElettore.fxml"));
	    			            root = (Parent) fxmlLoader.load();
	    			            AreaElettoreController controller = fxmlLoader.getController();
	    			            Elettore elettore = new Elettore(u);
	    			            controller.setElettore(elettore);
	    					}
	    	    		}
    	    			root.requestFocus();
    	    			Scene scene = new Scene(root, 570, 420);
		    	        stage.setTitle("SistemaVotoScrutinio");
		    	        stage.setScene(scene);
		    	        stage.show();
	    	    	
    	    		}catch(IOException e) {
    	    			e.printStackTrace();
    	    		}
    	    	
    	    	}
    		
    			
    		}
				
		});
    }
    
    @FXML
    void handleEmail(ActionEvent event) {}

    @FXML
    void handlePassword(ActionEvent event) {}
    
    int i=0;
    
    @FXML
    void handleOK(MouseEvent event) throws Exception{		
    	
		
    }

}