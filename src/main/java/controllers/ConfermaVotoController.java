package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;

import database.CandidatoDAO;
import database.CandidatoDAOImpl;

import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Cae;
import model.Candidato;
import model.Elettore;
import model.Partito;
import model.Preferenza;
import model.Quesito;
import model.SchedaElettorale;
import model.Voce;

public class ConfermaVotoController {
	private Cae cae;
	private Elettore elettore;
	private SchedaElettorale schedaElettorale;
	private Preferenza preferenza;
	
	public void setElettore(Elettore e) {
		elettore = e;
	}
	
	public void setCae(Cae c) {
		cae = c;
	}
	
	public void setSchedaElettoraleEPreferenza (SchedaElettorale s, Preferenza p) {
		preferenza = p;
		schedaElettorale = s;
		if(preferenza.bianca()) {
    		lblInfo.setText("Scheda bianca");
    	}else {
    		lblInfo.setText(schedaElettorale.getModVoto().toStringPreferenza(preferenza));	
    	}
	}
	
    @FXML
    private Button btnConferma;

    @FXML
    private Button btnModifica;

    @FXML
    private Label lblInfo;
    
    @FXML
    void handleConferma(ActionEvent event) throws IOException{
    	if(!preferenza.bianca()) {
    		elettore.esprimiPreferenza(schedaElettorale, preferenza);
    	}
    	
    	if(Objects.isNull(cae)) {
    		Stage stage = (Stage) btnConferma.getScene().getWindow();
	    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/areaElettore.fxml"));    	
	    	Parent root = (Parent) fxmlLoader.load();
	        AreaElettoreController controller = fxmlLoader.getController();
	        controller.setElettore(elettore);
	        
	    	Scene scene = new Scene(root, 570, 420);
	        stage.setTitle("SistemaVotoScrutinio");
	        stage.setScene(scene);
	        root.requestFocus();
	        return;
    	}else {
    		Stage stage = (Stage) btnConferma.getScene().getWindow();
	    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/areaCAE.fxml"));    	
	    	Parent root = (Parent) fxmlLoader.load();
	        AreaCAEController controller = fxmlLoader.getController();
	        controller.setCae(cae);
	        
	    	Scene scene = new Scene(root, 570, 420);
	        stage.setTitle("SistemaVotoScrutinio");
	        stage.setScene(scene);
	        root.requestFocus();
    	}
    
    }

    @FXML
    void handleModifica(ActionEvent event) throws IOException{
    	Stage stage = (Stage) btnConferma.getScene().getWindow();
    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/areaVoto.fxml"));    	
    	Parent root = (Parent) fxmlLoader.load();
        AreaVotoController controller = fxmlLoader.getController();
        controller.setElettore(elettore);
        controller.setCae(cae);
        controller.setSchedaElettorale(new SchedaElettorale(schedaElettorale.getId(), schedaElettorale.getDescrizione(),  schedaElettorale.getInformazione(), schedaElettorale.getLimiteEta(), schedaElettorale.getModVoto(), schedaElettorale.getModCalcoloVincitore(), schedaElettorale.getQuorum()));
        
    	Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        root.requestFocus();
    }

}
