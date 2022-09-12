package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.Cae;
import model.Candidato;
import model.Partito;
import model.SistemaVotoScrutinio;

public class CreaPartitoController {
	private Cae cae;

    @FXML
    private Button btnCreaPartito;

    @FXML
    private Button btnGetBack;

    @FXML
    private ChoiceBox<Candidato> choiceCapoPartito;

    @FXML
    private TextField lblNome;

    @FXML
    private GridPane paneCandidati;

    @FXML
    private Label lblMessage;
    
    List<CheckBox> checkboxes = new ArrayList<CheckBox>();
    Set<Candidato> candidati;
    
    @FXML
    public void initialize() {
    	candidati = SistemaVotoScrutinio.getIstanza().getCandidatiSenzaPartito();
    	if(candidati.isEmpty()) {
    		lblMessage.setText("Non esistono candidati che non appartengono ad un partito");
    		lblMessage.setVisible(true);
    		choiceCapoPartito.setDisable(true);
    	}else {
    		choiceCapoPartito.setItems(FXCollections.observableArrayList(candidati));
    		
    		TilePane r = new TilePane();
        	
        	for(Candidato c : candidati) {
        		checkboxes.add(new CheckBox(c.toString()));
        		CheckBox attuale = checkboxes.get(checkboxes.size()-1);
        		r.getChildren().add(attuale);
        	}
        	r.setAlignment(Pos.CENTER);
        	paneCandidati.add(r,0,0);
    	}    		
    }
    
    public void setCae(Cae c) {
    	this.cae = c; 
    }

    @FXML
    void handleNome(ActionEvent event) {}


    @FXML
    void handleCapoPartito(MouseEvent event) {
    	
    }

    @FXML
    void handleChoiceModVoto(MouseEvent event) {}

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
    void handleBtnCreaPartito(ActionEvent event) throws IOException{
    	String nome = lblNome.getText();
    	Candidato capoPartito = choiceCapoPartito.getValue();
    	Set<Candidato> candidatiScelti = new HashSet<Candidato>();
    	for(CheckBox c : checkboxes) {
			if(c.isSelected()) {
				for(Candidato candidato : candidati)
					if(c.getText().equals(candidato.toString())) {
						candidatiScelti.add(candidato);
					}
				
			}
		}
    	
    	if(!Objects.isNull(nome) && !nome.isEmpty() && !nome.trim().isEmpty()) {
    		if(!Objects.isNull(capoPartito)) {
    			if(!Objects.isNull(candidatiScelti)) {
    				if(candidatiScelti.contains(capoPartito)) {
    					(new Partito(nome, capoPartito, candidatiScelti)).crea();
            		
	            		Stage stage = (Stage) btnCreaPartito.getScene().getWindow();
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
                		lblMessage.setText("Il capo partito deve essere uno dei candidati del partito");
    				}
            		
            	}else {
            		lblMessage.setVisible(true);
            		lblMessage.setText("Inserire candidati");
            	}
        	}else {
        		lblMessage.setVisible(true);
        		lblMessage.setText("Inserire capo partito");
        	}
    	}else {
    		lblMessage.setVisible(true);
    		lblMessage.setText("Inserire nome");
    	}

    }

}