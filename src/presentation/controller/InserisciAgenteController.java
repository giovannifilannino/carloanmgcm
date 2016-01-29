package presentation.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import presentation.controller.utility.Popup;
import integration.CarLoanDB;
import business.entity.Agente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InserisciAgenteController {

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
	
	CarLoanDB db = new CarLoanDB();
	
	ResultSet agenzieset;
	ObservableList<String> agenzialist = FXCollections.observableArrayList();
	
	public void initialize() throws SQLException{
		agenzieset = db.getAgenzie();
		while(agenzieset.next()){
			agenzialist.add(agenzieset.getString("nomeagenzia"));
		}
		agenzie.setItems(agenzialist);
	}
	
	public void conferma(ActionEvent e) throws SQLException{
		Stage esc =  (Stage) esci_btn.getScene().getWindow();
		if(username.getText() != null && password.getText() != null && nome.getText() != null && agenzie.getValue() != null){
			db.setAgente(username.getText(), password.getText(), nome.getText(), cognome.getText(), agenzie.getValue().toString());
			Agente agente = new Agente(agenzie.getValue().toString(), nome.getText(), cognome.getText(), username.getText());
			AmministratoreController.agente.add(agente);
			esc.close();
		}else{
			Popup.Errore("Errore inserimento.", "Non hai inserito tutte le informazioni necessarie.");
			}
	}
	
	public void esci(ActionEvent e){
		Stage esc =  (Stage) esci_btn.getScene().getWindow();
		esc.close();
	}
	
	
}
