package controllers;

import java.io.IOException;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import database.SessioneDAO;
import database.SessioneDAOImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Cae;
import model.Candidato;
import model.Sessione;
import model.InformazioneScheda;
import model.SchedaElettorale;
import model.SistemaVotoScrutinio;

public class CreaSessioneController{
	private Cae cae;
	private Set<SchedaElettorale> schedeElettorali;

	@FXML
	private Label lblMessage;
	
	@FXML
    private Button btnGetBack;
	
    @FXML
    private Button btnCreaSessione;

    @FXML
    private Button btnCreaSchedaElettorale;

    @FXML
    private TextField lblLuogo;
    
    @FXML
    private TextArea textSessione;
    
    @FXML
    private GridPane paneSchedeElettorali;

    @FXML
    private DatePicker dataFine;

    @FXML
    private DatePicker dataInizio;
    
    public void setCae(Cae c) {
    	this.cae = c;
    }
    
    private Set<CheckBox> checkboxes;
    @FXML
    public void initialize() {
    	textSessione.setPromptText("Inserire la descrizione della sessione");
    	dataInizio.setValue(LocalDate.now());
    	Callback<DatePicker, DateCell> dayCellFactory = null;
		dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
    	    @Override
    	    public void updateItem(LocalDate item, boolean empty) {
    	        super.updateItem(item, empty);
    	        if (item.isBefore(LocalDate.now())) { 
    	            setDisable(true);
    	        }
    	    }
	    };
		dataInizio.setDayCellFactory(dayCellFactory);
    	dataInizio.valueProperty().addListener(new ChangeListener<LocalDate>() {
    		@Override
		    public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
    			Callback<DatePicker, DateCell> dayCellFactory = null;
    			dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
		    	    @Override
		    	    public void updateItem(LocalDate item, boolean empty) {
		    	        super.updateItem(item, empty);
		    	        if (item.isBefore(dataInizio.getValue())) { 
		    	            setDisable(true);
		    	        }
		    	    }
	    	    };
	    		dataFine.setDayCellFactory(dayCellFactory);
		    }
    	});
    	
    	TilePane r = new TilePane();
    	checkboxes = new HashSet<>();
    	schedeElettorali = new HashSet<>();
    	for(SchedaElettorale s : SistemaVotoScrutinio.getIstanza().getSchedeElettorali()) {
    		CheckBox checkbox = new CheckBox(s.toString());
    		checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(newValue) {
						schedeElettorali.add(s);
					}else {
						schedeElettorali.remove(s);
					}
					
				}
			});
    		checkboxes.add(checkbox);
    		r.getChildren().add(checkbox);
    	}
    	r.setAlignment(Pos.CENTER);
    	paneSchedeElettorali.add(r, 0, 0);
    
    }
    
    @FXML
    void handleBtnCreaSchedaElettorale(ActionEvent event) throws IOException{    		
    	Stage stage = (Stage) btnCreaSessione.getScene().getWindow();
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
    void handleBtnCreaSessione(ActionEvent event) throws IOException{
    	String descrizione = textSessione.getText();
    	String luogo = lblLuogo.getText();
    	ZoneId defaultZoneId = ZoneId.systemDefault();
    	LocalDate localInizio = dataInizio.getValue();
    	LocalDate localFine = dataFine.getValue();
    	if(!Objects.isNull(luogo) && !luogo.isEmpty() && !luogo.trim().isEmpty()) {
    		if(!Objects.isNull(descrizione) && !descrizione.isEmpty() && !descrizione.trim().isEmpty()) {
	    		if(!Objects.isNull(schedeElettorali) && !schedeElettorali.isEmpty()) {
	    			if(!Objects.isNull(localInizio)) {
	    				Date inizio = Date.from(localInizio.atStartOfDay(defaultZoneId).toInstant());
	    				if(!Objects.isNull(localFine)) {
	    					Date fine = Date.from(localFine.atStartOfDay(defaultZoneId).toInstant());
	    					Sessione sessione = new Sessione(descrizione, inizio, fine, schedeElettorali, luogo);
	    					sessione.crea();
				    		Stage stage = (Stage) btnCreaSessione.getScene().getWindow();
				        	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/areaCAE.fxml"));
				        	Parent root = (Parent) fxmlLoader.load();
				            AreaCAEController controller = fxmlLoader.getController();
				            controller.setCae(cae);
				            
				        	Scene scene = new Scene(root, 570, 420);
				            stage.setTitle("SistemaVotoScrutinio");
				            stage.setScene(scene);
				            stage.show();
				            root.requestFocus();
	    				}else {
	    					lblMessage.setText("Scegliere la data di fine della sessione");
	        				lblMessage.setVisible(true);
	    				}
	    				
	    			}else {
	    				lblMessage.setText("Scegliere la data di inizio della sessione");
	    				lblMessage.setVisible(true);
	    			}
	    			
	    		}else {
	    			lblMessage.setText("Scegliere le schede elettorali da inserire nella sessione");
	        		lblMessage.setVisible(true);
	    		}
	       	}else {
	    		lblMessage.setText("Inserire una descrizione");
	    		lblMessage.setVisible(true);
	    	}
    	}else {
    		lblMessage.setText("Inserire un luogo");
    		lblMessage.setVisible(true);
    	}
    	  
    }
    
	@FXML
    void handleGetBack(ActionEvent event) throws IOException{
    	Stage stage = (Stage) btnGetBack.getScene().getWindow();
    	FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/views/areaCAE.fxml"));    	
    	Parent root = (Parent) fxmlLoader.load();
        AreaCAEController controller = fxmlLoader.getController();
        controller.setCae(cae);
        
    	Scene scene = new Scene(root, 570, 420);
        stage.setTitle("SistemaVotoScrutinio");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

}
