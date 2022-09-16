package controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
import model.Cae;
import model.Sessione;
import model.Elettore;
import model.SchedaElettorale;
import model.SistemaVotoScrutinio;

public class AreaElettoreController {

	private Elettore elettore;
	private Cae cae;
	
	public void setElettore(Elettore e) {
		this.elettore = e;
		Set<SchedaElettorale> schedeElettorali = elettore.getSchedeElettoraliSenzaPreferenza();
    	if(!Objects.isNull(schedeElettorali) && !schedeElettorali.isEmpty()) {
    		choiceSchedaElettorale.setItems(FXCollections.observableArrayList(schedeElettorali));
    	}else {
    		lblMessage.setText("Al momento non sono votabili schede elettorali");
    		lblMessage.setVisible(true);
    		btnVota.setDisable(true);
    	}
	}
	
	
	public void setCae(Cae c) {
		cae = c;
	}
    @FXML
    private Button btnGetBack;
    
    @FXML
    private Button btnVota;

    @FXML
    private ChoiceBox<SchedaElettorale> choiceSchedaElettorale;
    
    @FXML
    private Label lblMessage;

    @FXML
    void handleChoiceModVoto(MouseEvent event) {}

    @FXML
    void handleGetBack(ActionEvent event) throws IOException{
    	if(Objects.isNull(cae)) {
    		elettore.uscita();
	    	Stage stage = (Stage) btnGetBack.getScene().getWindow();
	    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/benvenuto.fxml")); 
	    	Parent root = fxmlLoader.load();
	    	Scene scene = new Scene(root, 570, 420);
	        stage.setTitle("SistemaVotoScrutinio");
	        stage.setScene(scene);
	        stage.show();
	        root.requestFocus();
	        return;
    	}
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
        return;
    }
    
    @FXML
    void handleVota(ActionEvent event) throws IOException{
    	SchedaElettorale selezione = choiceSchedaElettorale.getValue();
    	if(!Objects.isNull(selezione)) {
    		Stage stage = (Stage) btnVota.getScene().getWindow();
	    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/areaVoto.fxml")); 
	    	Parent root = (Parent) fxmlLoader.load();
	        AreaVotoController controller = fxmlLoader.getController(); 
	        controller.setElettore(elettore);
	        controller.setSchedaElettorale(selezione);
	        if(!Objects.isNull(cae)) controller.setCae(cae);	
	            	
	        Scene scene = new Scene(root, 570, 420);
	        stage.setTitle("SistemaVotoScrutinio");
	        stage.setScene(scene);
	        stage.show();
	        root.requestFocus();
    	}else {
    		lblMessage.setText("Inserire la scheda elettorale nelle Sessioni in corso per la quale si desidera esprimere una preferenza");
    		lblMessage.setVisible(true);
    	}
    	
    }
    
}
