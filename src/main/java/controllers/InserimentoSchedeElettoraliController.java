package controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Cae;
import model.Elettore;
import model.SchedaElettorale;

public class InserimentoSchedeElettoraliController {
	private Cae cae;

    @FXML
    private Button btnInserireSchedeElettorali;
    
    @FXML
    private Button btnScegliElettore;
    
	@FXML
	private Button btnElettoreEspressaPreferenza;
	
	@FXML
	private Button btnGetBack;
	
	@FXML
	private ChoiceBox<SchedaElettorale> choiceSchedaElettorale;

    @FXML
    private TextField lblEmail;
    
    @FXML
    private Label lblMessage;
    
    @FXML
    private Label lblMessageSceltaElettore;
    
    public void setCae(Cae c) {
    	this.cae = c;
    	choiceSchedaElettorale.setDisable(true);
    	btnElettoreEspressaPreferenza.setDisable(true);
    }

    @FXML
    void handleBtnInserireSchedeElettorali(ActionEvent event) throws IOException{
    	Stage stage = (Stage) btnInserireSchedeElettorali.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/areaElettore.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AreaElettoreController controller = fxmlLoader.getController();
        controller.setUtente(cae);
        root.requestFocus();
        Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }
    
    String email;
    
    @FXML 
    void handleBtnScegliElettore(ActionEvent event) {
    	email=lblEmail.getText();
    	lblMessage.setVisible(false);
    	boolean b=Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$").matcher(email).matches(); 
    	if(!b==false) {
			if(Elettore.registrato(email)) {
				Elettore e = new Elettore(email);
				Set<SchedaElettorale> schedeElettorali = new HashSet<>();
				for(SchedaElettorale s : cae.getSchedeElettoraliConElettori()) {
					if(e.getSchedeElettoraliSenzaPreferenza().contains(s)) schedeElettorali.add(s);
				}
				if(schedeElettorali.isEmpty()){
					lblMessage.setText("Non sono disponibili consultazioni per l'elettore inserito");
		        	lblMessage.setVisible(true);
				}else btnElettoreEspressaPreferenza.setDisable(false);
				choiceSchedaElettorale.setItems(FXCollections.observableArrayList(schedeElettorali));
				choiceSchedaElettorale.setDisable(false);
			}else {
				lblMessageSceltaElettore.setText("Non è registrato un elettore associato alla mail inserita");
				lblMessageSceltaElettore.setVisible(true);
			}
    	}else {
    		lblMessageSceltaElettore.setVisible(true);
    		lblMessageSceltaElettore.setText("Formato email non valido");
    	}    
    }
    
    @FXML
	void handleBtnElettoreEspressaPreferenza(ActionEvent event) throws IOException{
    	SchedaElettorale schedaElettorale = choiceSchedaElettorale.getValue();
    	Elettore e = new Elettore(email);
		if(!Objects.isNull(schedaElettorale)) {
			cae.elettoreEspressaPreferenza(e, schedaElettorale);
		}else {
			lblMessage.setText("Inserire scheda elettorale");
        	lblMessage.setVisible(true);
		}
				
	}

    @FXML
    void handleEmail(ActionEvent event) {

    }
    
    @FXML
    void handleGetBack(ActionEvent event) throws IOException{
		Stage stage = (Stage) btnGetBack.getScene().getWindow();
    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/areaCAE.fxml")); 
    	Parent root = fxmlLoader.load();
    	AreaCAEController controller = fxmlLoader.getController();
    	controller.setCae(cae);
    	Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }
}





