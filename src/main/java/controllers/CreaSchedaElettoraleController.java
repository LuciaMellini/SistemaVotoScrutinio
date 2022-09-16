package controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import model.Cae;
import model.Candidato;
import model.Sessione;
import model.InformazioneScheda;
import model.ModCalcoloVincitore;
import model.ModVoto;
import model.Partito;
import model.Quesito;
import model.SchedaElettorale;
import model.SistemaVotoScrutinio;
import model.Voce;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.event.EventHandler;

public class CreaSchedaElettoraleController {
	
	private Cae cae;
	private Sessione sessione;
	private InformazioneScheda informazioneScheda;
	
	@FXML
    private Button btnGetBack;
	
    @FXML
    private Button btnCrea;
    
    @FXML
    private Label lblMessage;

    @FXML
    private ChoiceBox<ModCalcoloVincitore> choiceModCalcoloVinc;

    @FXML
    private ChoiceBox<ModVoto> choiceModVoto;

    @FXML
    private Spinner<Integer> spinnerEta;
    
    @FXML
    private Spinner<Integer> spinnerQuorum;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Label lblInfo;
    
    @FXML
    private GridPane paneOpzioni;
    
    @FXML
    private TextArea textDescrizione;


    public void setCae(Cae c) {
    	this.cae = c;
    }
    
    public void setSessione(Sessione c) {
		this.sessione = c;
	}
    
    @FXML
    private void initialize() {
    	spinnerEta.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(18,30));
    	spinnerQuorum.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100));
    	choiceModVoto.setItems(FXCollections.observableArrayList(ModVoto.values()));
    	choiceModVoto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModVoto>() {

			@Override
			public void changed(ObservableValue<? extends ModVoto> observable, ModVoto oldValue, ModVoto newValue) {
				if(newValue.equals(ModVoto.REFERENDUM)) {
	    			choiceModCalcoloVinc.setValue(ModCalcoloVincitore.MAGGIORANZA);
	    			choiceModCalcoloVinc.setDisable(true);
	    		}
	    		else{
	    			choiceModCalcoloVinc.setItems(FXCollections.observableArrayList(choiceModVoto.getValue().possibiliModCalcoloVincitore()));
	    			choiceModCalcoloVinc.setDisable(false);    			
	    		}
	    		newValue.handleViewCreazione(lblInfo, paneOpzioni, informazioneScheda, cae);
				
			}
		});
    	choiceModCalcoloVinc.setDisable(true);
    	informazioneScheda = new InformazioneScheda();
	}
   

    @FXML
    void handleChoiceModCalcoloVinc(MouseEvent event) {
    }
    
   
    @FXML
    void handleChoiceModVoto(MouseEvent event) {
    	
    }
    
    public static void handleSceltaCandidatiPartiti(Label lblInfo, GridPane paneOpzioni, InformazioneScheda informazioneScheda, Cae cae) {
    	lblInfo.setVisible(true);
    	lblInfo.setText("Scegliere la categoria tra cui andrà effettuata la scelta");
    	TilePane r = new TilePane();
        ToggleGroup tg = new ToggleGroup();
        RadioButton r1 = new RadioButton("Partiti");
        RadioButton r2 = new RadioButton("Candidati");
        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);
        r.getChildren().add(r1);
        r.getChildren().add(r2);
        r.setAlignment(Pos.CENTER);

    	paneOpzioni.getChildren().clear();
        paneOpzioni.add(r, 0, 0);
    
    	tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				Toggle selected = newValue;
				if(!Objects.isNull(selected)) {
		        	if(((RadioButton) tg.getSelectedToggle()).equals(r1)) {
		        		handleInformazioneSchedaPartiti(lblInfo, paneOpzioni, informazioneScheda, cae, false);
		        	}else {
		        		handleInformazioneSchedaCandidati(lblInfo, paneOpzioni, informazioneScheda, cae);
		        	}
				}
			}
    		
    	});
		
    }
    
    public static void handleInformazioneSchedaCandidati(Label lblInfo, GridPane paneOpzioni, InformazioneScheda informazioneScheda, Cae cae) {
    	lblInfo.setVisible(true);
    	lblInfo.setText("Scegliere i candidati da inserire nella scheda");
    	paneOpzioni.getChildren().clear();
    	
    	TilePane r = new TilePane();
    	List<CheckBox> checkboxes = new ArrayList<CheckBox>();
    	Set<Candidato> candidati = SistemaVotoScrutinio.getIstanza().getCandidati();
    	for(Candidato c : candidati) {
    		CheckBox checkbox = new CheckBox(c.toString());
    		checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(newValue) {
						informazioneScheda.add(c);
					}else {
						informazioneScheda.remove(c);
					}
					
				}
			});
    		
    		checkboxes.add(checkbox);
    		r.getChildren().add(checkbox);
    	}
    	r.setAlignment(Pos.CENTER);
    	paneOpzioni.add(r, 0, 0);
    	
    	 	
    	Button btnCreaCandidato = new Button();
		btnCreaCandidato.setText("Crea candidato");

		GridPane.setHalignment(btnCreaCandidato, HPos.CENTER);		
		paneOpzioni.add(btnCreaCandidato, 0, 1);
		EventHandler<ActionEvent> handleBtnCreaCandidato = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				Stage stage = (Stage) btnCreaCandidato.getScene().getWindow();
		    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/creaCandidato.fxml"));
		    	try {
		    		Parent root = (Parent) fxmlLoader.load();   
		    		CreaCandidatoController controller = fxmlLoader.getController();
		    		controller.setCae(cae);
			        Scene scene = new Scene(root, 570, 420);
			        stage.setTitle("SistemaVotoScrutinio");
			        stage.setScene(scene);
			        stage.show();
			        root.requestFocus();
		    	}catch (IOException e) {
					System.out.println(e.getStackTrace());
				}
		    	
			};
		};
		
		btnCreaCandidato.setOnAction(handleBtnCreaCandidato);    	
    }
    
    public static void handleInformazioneSchedaPartiti(Label lblInfo, GridPane paneOpzioni, InformazioneScheda informazioneScheda, Cae cae, boolean inserisciCandidati){   	
    	lblInfo.setVisible(true);
    	lblInfo.setText("Scegliere i partiti da inserire nella scheda");
    	
    	paneOpzioni.getChildren().clear();
    	
    	TilePane r = new TilePane();
    	List<CheckBox> checkboxes = new ArrayList<CheckBox>();
    	Set<Partito> partiti = SistemaVotoScrutinio.getIstanza().getPartiti();
    	for(Partito p : partiti) {
    		CheckBox checkbox = new CheckBox(p.toString());
    		checkboxes.add(checkbox);
    		r.getChildren().add(checkbox);
    		
    		checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(newValue) {
						informazioneScheda.add(p);
						for(Candidato c:p) {
							informazioneScheda.add(c);
						}
					}else {
						informazioneScheda.remove(p);
						for(Candidato c:p) {
							informazioneScheda.remove(c);
						}
					}
					
				}
			});
    	}
    	r.setAlignment(Pos.CENTER);
    	paneOpzioni.add(r, 0, 0);
    	
		    	
    	Button btnCreaPartito = new Button();
		btnCreaPartito.setText("Crea partito");

		GridPane.setHalignment(btnCreaPartito, HPos.CENTER);		
		paneOpzioni.add(btnCreaPartito, 0, 1);
		EventHandler<ActionEvent> handleBtnCreaPartito = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				Stage stage = (Stage) btnCreaPartito.getScene().getWindow();
		    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/creaPartito.fxml"));
		    	try {
		    		Parent root = (Parent) fxmlLoader.load();
			        CreaPartitoController controller = fxmlLoader.getController();
			        controller.setCae(cae);
			        
			    	Scene scene = new Scene(root, 570, 420);
			        stage.setTitle("SistemaVotoScrutinio");
			        stage.setScene(scene);
			        stage.show();
			        root.requestFocus();
		    	}catch(IOException e) {
		    		System.out.println(e.getStackTrace());
		    	}
		    	
			};
		};
		
		btnCreaPartito.setOnAction(handleBtnCreaPartito);
		
	}
    
    public static void handleInformazioneSchedaReferendum(Label lblInfo, GridPane paneOpzioni, InformazioneScheda informazioneScheda, Cae cae) {
    	lblInfo.setVisible(false);
    	TextArea textQuesito = new TextArea();
		textQuesito.setVisible(true);
		textQuesito.setPromptText("Inserisci il testo del quesito");
		
		textQuesito.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				informazioneScheda.remove(new Quesito(oldValue));
				informazioneScheda.add(new Quesito(newValue));
			}
		});
	
    	paneOpzioni.getChildren().clear();
		paneOpzioni.add(textQuesito, 0, 0);
		textQuesito.setMaxHeight(150);
		textQuesito.setMaxWidth(400);
		GridPane.setHalignment(textQuesito, HPos.CENTER);
    }

    @FXML
    void handleSpinnerEta(MouseEvent event) {

    }
    
    @FXML
    void handlebtnCrea(ActionEvent event) throws IOException{  
    	int limiteEta = spinnerEta.getValue();
    	int quorum = spinnerQuorum.getValue();
    	ModVoto modVoto = choiceModVoto.getValue();
    	ModCalcoloVincitore modCalcoloVincitore = choiceModCalcoloVinc.getValue();
    	String descrizione = textDescrizione.getText();
    	if(!Objects.isNull(modVoto)) {
    		if(!Objects.isNull(modCalcoloVincitore)) {
    			if(!Objects.isNull(informazioneScheda) && !informazioneScheda.isEmpty()) {
    				if(!Objects.isNull(descrizione) && !descrizione.isEmpty() && !descrizione.trim().isEmpty()) {
    					SchedaElettorale schedaElettorale = new SchedaElettorale(descrizione, informazioneScheda, limiteEta, modVoto, modCalcoloVincitore, quorum);
            			cae.creaSchedaElettorale(schedaElettorale);
	            		Stage stage = (Stage) btnGetBack.getScene().getWindow();
	                	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/creaSessione.fxml"));    	
	                	Parent root = (Parent) fxmlLoader.load();
	                    CreaSessioneController controller = fxmlLoader.getController();
	                    controller.setCae(cae);
	                    
	                	Scene scene = new Scene(root, 770, 620);
	                    stage.setTitle("SistemaVotoScrutinio");
	                    stage.setScene(scene);
	                    stage.show();
	                    root.requestFocus();
	            		
    				}else {
    					lblMessage.setVisible(true);
                		lblMessage.setText("Inserire la descrizione della scheda");
    				}
    				
            	}else {
            		lblMessage.setVisible(true);
            		lblMessage.setText("Inserire le informazioni della scheda");
            	}
        	}else {
        		lblMessage.setVisible(true);
        		lblMessage.setText("Inserire la modalità di calcolo del vincitore");
        	}
    	}else {
    		lblMessage.setVisible(true);
    		lblMessage.setText("Inserire la modalità di voto");
    	}
    }
    
    @FXML
    void handleGetBack(ActionEvent event) throws IOException{
    	Stage stage = (Stage) btnGetBack.getScene().getWindow();
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

}