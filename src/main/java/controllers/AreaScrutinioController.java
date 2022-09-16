package controllers;

import java.io.IOException;
import java.util.Objects;

import model.Cae;
import model.Risultato;
import model.Sessione;
import model.SchedaElettorale;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AreaScrutinioController {
	private Cae cae;
	
	@FXML
    private Button btnGetBack;

    @FXML
    private Button btnScrutinio;

    @FXML
    private ChoiceBox<SchedaElettorale> choiceSchedaElettorale;
    
    @FXML
    private Label lblMessage;
    
    @FXML
    private Label lblRisultato;
	
	public void setCae(Cae c) {
		this.cae = c;
		choiceSchedaElettorale.setItems(FXCollections.observableArrayList(cae.getSchedeElettoraliConElettori()));
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

    @FXML
    void handleScrutinio(ActionEvent event) throws IOException{
    	SchedaElettorale selezionato = choiceSchedaElettorale.getValue();
    	if(!Objects.isNull(selezionato)) {
    		cae.scrutinio(selezionato);
    		lblRisultato.setText(selezionato.toStringRisultato());
    	}else {
    		lblMessage.setText("Selezionare una scheda elettorale");
    	}
    }
}
