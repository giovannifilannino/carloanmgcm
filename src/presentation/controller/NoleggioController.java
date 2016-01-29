package presentation.controller;

import integration.CarLoanDB;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import business.entity.Azienda;
import business.entity.Cliente;
import business.entity.Contratto;
import presentation.StageController;
import presentation.controller.utility.Popup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class NoleggioController extends StageController{

	@FXML
	Button esci_btn;
	@FXML
	Button avanti_btn;
	@FXML
	RadioButton chilometraggio_limitato;
	@FXML
	RadioButton chilometraggio_illimitato;
	@FXML
	RadioButton noleggio_giornaliero;
	@FXML
	RadioButton noleggio_settimanale;
	@FXML
	DatePicker inizio_noleggio;
	@FXML
	DatePicker fine_noleggio;
	@FXML
	ComboBox agenzia; 
	
	
	
	ToggleGroup chilometraggio = new ToggleGroup();
	ToggleGroup noleggio = new ToggleGroup();
	
	ClientiController c = new ClientiController();
	
	static Contratto nuovo_contratto = new Contratto();
	
	LocalDate datainizio;
	LocalDate datafine;
	
	static boolean dati = false;
	
	CarLoanDB db = new CarLoanDB();
	ObservableList<String> agenzie;
	ResultSet agenzieres;
	
	
	
	@FXML
	public void initialize() throws SQLException{
		agenzieres = db.getAgenzie();
		chilometraggio_limitato.setToggleGroup(chilometraggio);
		chilometraggio_illimitato.setToggleGroup(chilometraggio);
		noleggio_giornaliero.setToggleGroup(noleggio);
		noleggio_settimanale.setToggleGroup(noleggio);
		agenzie = FXCollections.observableArrayList();
		setAgenzie();
		agenzia.setItems(agenzie);
		
		if(dati){
			inizio_noleggio.setValue(nuovo_contratto.getData_inizio());
			agenzia.setValue(nuovo_contratto.getPrelievo().toString());
			fine_noleggio.setValue(nuovo_contratto.getData_fine());
			if(nuovo_contratto.getChilometraggio_limitato().equalsIgnoreCase("limitato"))
				chilometraggio_limitato.setSelected(true);
			else
				chilometraggio_illimitato.setSelected(true);
			if(nuovo_contratto.getNoleggio().equalsIgnoreCase("giornaliero"))
				noleggio_giornaliero.setSelected(true);
			else
				noleggio_giornaliero.setSelected(true);
		}
	}
	
	public void show(){
		super.setController("FinestraPrenotazione");
		super.show();
	}
	
	private void setAgenzie() throws SQLException{
		while(agenzieres.next()){
			agenzie.add(agenzieres.getString("NomeAgenzia"));
		}
	}
	
	@FXML
	public void avanti(ActionEvent e){
		System.out.println();
		String errore = "";
		boolean errore2 = false;
		if(chilometraggio.getSelectedToggle() == null && noleggio.getSelectedToggle()==null){
			Alert alert = new Alert(AlertType.ERROR);
			errore = errore + "Non hai selezionato nulla in chilometraggio e in noleggio";
			errore2 = true;
			
		} else if(!checkData(inizio_noleggio, fine_noleggio)){
			errore = errore + "Data inizio e fine non valide";
			errore2 = true;
		} else if(agenzia.getValue() == null){
			errore = errore + "Non hai inserito nessuna agenzia da cui prendere l'auto";
			errore2 = true;
		}
			
		
		if(errore2){
			Popup.Errore("Errore inserimento", errore);
		}
		if(chilometraggio.getSelectedToggle() != null && noleggio.getSelectedToggle()!=null && checkData(inizio_noleggio, fine_noleggio) && agenzia.getValue() != null){
			nuovo_contratto.setChilometraggio_limitato(chilometraggio_limitato.isSelected());
			nuovo_contratto.setNoleggio(noleggio_giornaliero.isSelected());
			nuovo_contratto.setData_inizio(inizio_noleggio.getValue());
			nuovo_contratto.setData_fine(fine_noleggio.getValue());
			nuovo_contratto.setPrelievo(new Azienda(agenzia.getValue().toString()));
			nuovo_contratto.setCliente(new Cliente(LoginController.username));
			loadPrenotazioneAuto();
		}
	}
	
	private void loadPrenotazioneAuto(){
		try {
			dati = true;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("fxmlclass/FinestraPrenotazioneAutomobile.fxml"));
			ClientiController.prenotazione1.setScene(new Scene(loader.load()));
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}
	
	@FXML
	public void esci(ActionEvent e){
		Stage stage = (Stage) esci_btn.getScene().getWindow();
		stage.close();
	}
	
	public boolean checkData(DatePicker d1, DatePicker d2){
		LocalDate datainizio = d1.getValue();
		LocalDate datafine = d2.getValue();
		if(datainizio==null || datafine==null){
			return false;
		}
		if(datainizio.isBefore(datafine)){
			return true;	
		}
		return false;
		
	}
	
	public Contratto getContratto(){
		return nuovo_contratto;
	}

}
