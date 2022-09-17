package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import database.SchedaElettoraleDAO;
import database.SchedaElettoraleDAOImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Cae;
import model.Candidato;
import model.Elettore;
import model.ModCalcoloVincitore;
import model.Partito;
import model.Preferenza;
import model.Quesito;
import model.SchedaElettorale;
import model.Utente;
import model.Voce;

public class AreaVotoController {
	private Utente utente;
	private SchedaElettorale schedaElettorale;
	private Preferenza preferenza;
	
	
	public void setUtente(Utente u) {
		this.utente = u;
	}
	
	public void setSchedaElettorale(SchedaElettorale s) {
		this.schedaElettorale = s;
		s.getModVoto().handleViewVoto(lblTitle, paneOpzioni, s, preferenza);
	}
	
    @FXML
    private Button btnVota;

    @FXML
    private Label lblTitle;
    
    @FXML
    private Label lblMessage;

    @FXML
    private GridPane paneOpzioni;
    
    public void initialize() {
    	preferenza = new Preferenza();
    }
    
    public static void handleVotoOrdinale(Label lblTitle, GridPane paneOpzioni, SchedaElettorale schedaElettorale, Preferenza preferenza) {
    	lblTitle.setText("Per ciascuna posizione scegliere la voce desiderata");
    	lblTitle.setVisible(true);
    	List<ChoiceBox<Voce>> choiceBoxes = new ArrayList<>();
    	Set<Voce> vociNonSelezionate = schedaElettorale.getInformazione().getVoci();
    	int numeroVociNonSelezionate = vociNonSelezionate.size();
    	for (int i=0; i<numeroVociNonSelezionate; i++) {
    		HBox hbox = new HBox();
    		Label label = new Label("Posizione " + Integer.valueOf(i+1).toString());
    		label.setPrefWidth(100);
    		hbox.getChildren().add(label);
    		ChoiceBox<Voce> choiceBox = new ChoiceBox<>();
    		choiceBoxes.add(choiceBox);
    		choiceBox.setItems(FXCollections.observableArrayList(vociNonSelezionate));
    		
    		choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Voce>() {

				@Override
				public void changed(ObservableValue<? extends Voce> observable, Voce oldValue, Voce newValue) {
					if(!Objects.isNull(oldValue)) vociNonSelezionate.add(oldValue);
					vociNonSelezionate.remove(newValue);
					for(ChoiceBox<Voce> c : choiceBoxes) {
						vociNonSelezionate.remove(newValue);
						if(!c.equals(choiceBox)) {
							if(Objects.isNull(c.getValue()) || c.getValue().equals(newValue)) c.setItems(FXCollections.observableArrayList(vociNonSelezionate));
						}
						
					}
				}
    			
			});
    		
    		choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Voce>() {

				@Override
				public void changed(ObservableValue<? extends Voce> observable, Voce oldValue, Voce newValue) {
					preferenza.remove(oldValue);
					preferenza.add(newValue, numeroVociNonSelezionate - choiceBoxes.indexOf(choiceBox));
				}
    			
			});
    		
    		hbox.getChildren().add(choiceBox);
    		hbox.setAlignment(Pos.CENTER);
    		paneOpzioni.add(hbox,0,i);
    		GridPane.setHalignment(hbox, HPos.CENTER);
    		GridPane.setValignment(hbox, VPos.CENTER);
    	} 
    }
    
    public static void handleVotoCategorico(Label lblTitle, GridPane paneOpzioni, SchedaElettorale schedaElettorale, Preferenza preferenza) {
    	lblTitle.setText("Scegliere una tra le possibili voci");
    	lblTitle.setVisible(true);
    	
    	ChoiceBox<Voce> choiceBox = new ChoiceBox<>();
    	choiceBox.setItems(FXCollections.observableArrayList(schedaElettorale.getInformazione().getVoci()));
        paneOpzioni.add(choiceBox, 0, 0);
    	GridPane.setHalignment(choiceBox, HPos.CENTER);
		GridPane.setValignment(choiceBox, VPos.CENTER);

		choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Voce>() {
			@Override
			public void changed(ObservableValue<? extends Voce> observable, Voce oldValue, Voce newValue) {
				preferenza.remove(oldValue);
				preferenza.add(newValue, 1);
			}
		});
		
    }
    
    public static void handleVotoCategoricoConPref(Label lblTitle, GridPane paneOpzioni, SchedaElettorale schedaElettorale, Preferenza preferenza) {
    	lblTitle.setText("Scegliere uno tra i possibili partiti, e se si desidera indicare la preferenza per uno dei suoi candidati");
    	lblTitle.setVisible(true);
    	Set<Partito> insiemePartiti = new HashSet<>();
    	for (Voce v: schedaElettorale.getInformazione()) {
    		if(v instanceof Partito) insiemePartiti.add((Partito) v);
    	}
    	
    	ChoiceBox<Partito> choiceBoxPartiti = new ChoiceBox<>();
    	choiceBoxPartiti.setItems(FXCollections.observableArrayList(insiemePartiti));
    	paneOpzioni.add(choiceBoxPartiti, 0, 0);
    	GridPane.setHalignment(choiceBoxPartiti, HPos.CENTER);
		GridPane.setValignment(choiceBoxPartiti, VPos.CENTER);
        
        ChoiceBox<Candidato> choiceBoxCandidati = new ChoiceBox<>();
        choiceBoxCandidati.setDisable(true);
        paneOpzioni.add(choiceBoxCandidati, 0, 1);
    	GridPane.setHalignment(choiceBoxCandidati, HPos.CENTER);
		GridPane.setValignment(choiceBoxCandidati, VPos.CENTER);
		
		choiceBoxPartiti.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Partito>() {
			@Override
			public void changed(ObservableValue<? extends Partito> observable, Partito oldValue, Partito newValue) {
				choiceBoxCandidati.setItems(FXCollections.observableArrayList(newValue.getCandidati()));
				choiceBoxCandidati.setDisable(false);
			}
			
		});
		
		choiceBoxPartiti.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Partito>() {
			@Override
			public void changed(ObservableValue<? extends Partito> observable, Partito oldValue, Partito newValue) {
				preferenza.remove(oldValue);
				preferenza.add(newValue, 1);
				if(Objects.isNull(choiceBoxCandidati.getValue())) preferenza.add(newValue.getCapoPartito(), 1);
			}
			
		});
		
		choiceBoxCandidati.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Candidato>() {
			@Override
			public void changed(ObservableValue<? extends Candidato> observable, Candidato oldValue, Candidato newValue) {
				if(Objects.isNull(oldValue) && !Objects.isNull(choiceBoxPartiti.getValue())) preferenza.remove(choiceBoxPartiti.getValue().getCapoPartito());
				preferenza.remove(oldValue);				
				preferenza.add(newValue, 1);
			}
			
		});
    }
    
    public static void handleReferendum(Label lblTitle, GridPane paneOpzioni, SchedaElettorale schedaElettorale, Preferenza preferenza) {
    	lblTitle.setText("Selezionare 'sì' se si è d'accordo con il quesito");
    	lblTitle.setVisible(true);
    	Label testoQuesito = new Label();
    	Quesito quesito = (Quesito) schedaElettorale.getInformazione().iterator().next();
    	testoQuesito.setText(quesito.toString());
    	paneOpzioni.add(testoQuesito,0,0);
    	GridPane.setHalignment(testoQuesito, HPos.CENTER);
    	GridPane.setValignment(testoQuesito, VPos.TOP);
    	
    	TilePane r = new TilePane();
        ToggleGroup tg = new ToggleGroup();
        RadioButton Si = new RadioButton("Sì");
        RadioButton No = new RadioButton("No");
        Si.setToggleGroup(tg);
        No.setToggleGroup(tg);
        r.getChildren().add(Si);
        r.getChildren().add(No);
        r.setAlignment(Pos.CENTER);
        
        paneOpzioni.add(r, 0, 0);

        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if(!Objects.isNull(oldValue)) preferenza.remove(quesito);
				if(newValue.equals(Si)) preferenza.add(quesito, 1);
				else preferenza.add(quesito, 0);
			}			
		});
    	
    }
    
    @FXML
    void handleBtnVota(ActionEvent event) throws IOException{
		Stage stage = (Stage) btnVota.getScene().getWindow();
	    FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/confermaVoto.fxml"));    	
	    Parent root = (Parent) fxmlLoader.load();
	    ConfermaVotoController controller = fxmlLoader.getController();
	    controller.setUtente(utente);
	    controller.setSchedaElettoraleEPreferenza(schedaElettorale, preferenza);
	    
	    Scene scene = new Scene(root, 570, 420);
	    stage.setTitle("SistemaVotoScrutinio");
	    stage.setScene(scene);
	    stage.show();
	    root.requestFocus();	    
    }

}
