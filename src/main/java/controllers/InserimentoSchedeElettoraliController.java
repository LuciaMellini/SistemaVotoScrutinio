package controllers;

import java.io.IOException;
import java.util.regex.Pattern;

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
import model.Elettore;

public class InserimentoSchedeElettoraliController {
	
	private Cae cae;
	
	public void setCae(Cae c) {
		cae = c;
	}

    @FXML
    private Button btnInserireSchedeElettorali;

    @FXML
    private TextField lblEmail;
    
    @FXML
    private Label lblMessage;

    @FXML
    void handleBtnInserireSchedeElettorali(ActionEvent event) throws IOException{
    	String email=lblEmail.getText();
    	
    	boolean b=Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$").matcher(email).matches(); 
    	if(b==false) {
    		lblMessage.setVisible(true);
    		lblMessage.setText("Formato email non valido");
    		return;
    	}
    	Stage stage = (Stage) btnInserireSchedeElettorali.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/areaElettore.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AreaElettoreController controller = fxmlLoader.getController();
        Elettore elettore = new Elettore(email);
        controller.setElettore(elettore);
        controller.setCae(cae);
        root.requestFocus();
        Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

    @FXML
    void handleEmail(ActionEvent event) {

    }
}

