package presentation.controller;

import integration.CarLoanDB;
import integration.ClienteDAO;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import business.entity.Agente;
import business.entity.Azienda;
import business.entity.Cliente;
import business.entity.Contratto;
import presentation.FrontController;
import presentation.SameStageController;
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

public class NoleggioController extends SameStageController{

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
	
	LocalDate datainizio;
	LocalDate datafine;
	
	Azienda ag = new Azienda();
	
	ObservableList<Azienda> agenzie;
	
	
	private boolean checkContratto(){
		boolean esito = false;
		if(!super.getContratto().emptyContratto()){
			esito = true;
		}
		return esito;
	}
	
	@FXML
	public void initialize() throws SQLException{
		chilometraggio_limitato.setToggleGroup(chilometraggio);
		chilometraggio_illimitato.setToggleGroup(chilometraggio);
		noleggio_giornaliero.setToggleGroup(noleggio);
		noleggio_settimanale.setToggleGroup(noleggio);
		agenzie = FXCollections.observableArrayList();
		setAgenzie();
		agenzia.setItems(agenzie);
		
		if(checkContratto()){
			inizio_noleggio.setValue(super.getContratto().getData_inizio());
			agenzia.setValue(super.getContratto().getPrelievo().toString());
			fine_noleggio.setValue(super.getContratto().getData_fine());
			if(super.getContratto().getChilometraggio_limitato().equalsIgnoreCase("limitato"))
				chilometraggio_limitato.setSelected(true);
			else
				chilometraggio_illimitato.setSelected(true);
			if(super.getContratto().getNoleggio().equalsIgnoreCase("giornaliero"))
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
		agenzie.addAll(ag.getAll());
	}
	
	ClienteDAO elemento=new ClienteDAO();
	
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
		} else {
			super.getContratto().setChilometraggio_limitato(chilometraggio_limitato.isSelected());
			super.getContratto().setNoleggio(noleggio_giornaliero.isSelected());
			super.getContratto().setData_inizio(inizio_noleggio.getValue());
			super.getContratto().setData_fine(fine_noleggio.getValue());
			super.getContratto().setAgenzia(new Azienda(agenzia.getValue().toString()));
			super.getContratto().setPrelievo(new Azienda(agenzia.getValue().toString()));
			//super.getContratto().setCliente(elemento.read(LoginController.username).getNome());
			loadPrenotazioneAuto();
		}
		if(errore2){
			Popup.Errore("Errore inserimento", errore);
		}
	}
	
	private void loadPrenotazioneAuto(){
		FrontController.getIstance().dispatchRequest("FinestraPrenotazioneAutomobile");
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
	
	@FXML
	@Override
	public void closeStage() {
		Stage stage = (Stage) esci_btn.getScene().getWindow();
		stage.close();
	}

}
