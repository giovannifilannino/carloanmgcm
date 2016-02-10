package presentation.controller;


import java.sql.SQLException;
import presentation.StageController;
import presentation.controller.utility.Popup;
import business.entity.Agente;
import business.entity.Azienda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InserisciAgenteController extends StageController{

	@Override
	public void show() {
		super.setController("InserimentoAgente");
		super.show();
	}

	@FXML
	TextField nome;
	@FXML
	TextField cognome;
	@FXML
	TextField username;
	@FXML
	ComboBox agenzie;
	@FXML
	PasswordField password;
	@FXML
	Button esci_btn;
	
	Azienda az = new Azienda();
	Agente ag = new Agente();
	
	ObservableList<Azienda> agenzialist = FXCollections.observableArrayList();
	
	public void initialize() throws SQLException{
		agenzialist.addAll(az.getAll());
		agenzie.setItems(agenzialist);
	}
	
	public void conferma(ActionEvent e) throws SQLException{
		
		if(username.getText() != null && password.getText() != null && nome.getText() != null && agenzie.getValue() != null){
			Agente agento = new Agente(agenzie.getValue().toString(), nome.getText(), cognome.getText(), username.getText(),password.getText());
			ag.create(agento);
			AmministratoreController.agente.add(agento);
			closeStage();
		}else{
			Popup.Errore("Errore inserimento.", "Non hai inserito tutte le informazioni necessarie.");
			}
	}
	
	
	@FXML
	@Override
	public void closeStage() {
		Stage esc =  (Stage) esci_btn.getScene().getWindow();
		esc.close();
	}
	
	
}
