package presentation.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import integration.CarLoanDB;
import business.entity.Automobile;
import business.entity.Azienda;
import presentation.controller.utility.Popup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class PrenotazioneAutomobileController {

	@FXML
	ComboBox categoria;
	@FXML
	ComboBox automobile;
	@FXML
	ComboBox agenzie;
	@FXML
	Button avanti_btn;
	@FXML 
	Button indietro_btn;
	@FXML
	TextField acconto;
	
	ObservableList<String> categorie;
	ObservableList<String> automobili;
	ObservableList<String> agenziee;
	static boolean dati = false;
	String targa;
	ResultSet agenzieset;
	ResultSet autoset;
	ResultSet categorieset;
	Hashtable<String,String> autodizio = new Hashtable<String,String>();
	CarLoanDB car = new CarLoanDB();
	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() throws SQLException{
	
		automobili = FXCollections.observableArrayList();
		categorie = FXCollections.observableArrayList();
		agenziee = FXCollections.observableArrayList();
		
		categorieset = car.getCategorie();
		agenzieset = car.getAgenzie();
		
		setAgenzie();
		setCategorie();
		
		categoria.setItems(categorie);
		
		agenzie.setItems(agenziee);
		
		if(dati){
			categoria.setValue(NoleggioController.nuovo_contratto.getCategoria());
			automobile.setValue(NoleggioController.nuovo_contratto.getAuto());
			agenzie.setValue(NoleggioController.nuovo_contratto.getRestituzione().toString());
		} else {
			automobile.setDisable(true);
			agenzie.setDisable(true);
		}
	}
	
	
	private void setAgenzie() throws SQLException{
		while(agenzieset.next()){
			agenziee.add(agenzieset.getString("NomeAgenzia"));
		}
	}
	
	private void setCategorie() throws SQLException{
		while(categorieset.next()){
			categorie.add(categorieset.getString("nomefascia"));
		}
	}
	
	
	@FXML
	public void setAutomobile(ActionEvent e) throws SQLException{
		automobili = FXCollections.observableArrayList();
		if(categoria.getValue()!=null){
			automobile.setDisable(false);
			autoset = car.getAuto(categoria.getValue().toString(), NoleggioController.nuovo_contratto.getPrelievo().toString());
			while(autoset.next()){
				automobili.add(autoset.getString("nomeauto"));
				String at = autoset.getString("nomeauto");
				String tg = autoset.getString("targa");
				
				autodizio.put(at, tg);
			}
			automobile.setItems(automobili);
			
		}
		
	}
	
	@FXML
	public void setAgenzia(ActionEvent e){
		
		if(automobile.getValue()!=null){
			agenzie.setDisable(false);
		}
		
	}
	
	
	@FXML
	public void datiOK(ActionEvent e){
		
		if(agenzie.getValue()!=null && checkAcconto(acconto.getText())){
			dati = true;
			NoleggioController.nuovo_contratto.setCategoria(categoria.getValue().toString());
			NoleggioController.nuovo_contratto.setAuto(new Automobile(automobile.getValue().toString()));
			NoleggioController.nuovo_contratto.setRestituzione(new Azienda(agenzie.getValue().toString()));
			NoleggioController.nuovo_contratto.setTarga(autodizio.get(automobile.getValue().toString()));
			NoleggioController.nuovo_contratto.setAcconto(restituisciAcconto());
		}
	}
	
	@FXML
	public void indietro(ActionEvent e){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("fxmlclass/FinestraPrenotazione.fxml"));
		try {
			ClientiController.prenotazione1.setScene(new Scene(loader.load()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@FXML
	public void avanti(ActionEvent e){
		if(dati){
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("fxmlclass/FinestraConfermaPrenotazione.fxml"));
			try {
				ClientiController.prenotazione1.setScene(new Scene(loader.load()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			Popup.Errore("Errore inserimento", "Non hai ancora completato tutti i campi.");
		}
		
	}
	
	
	private boolean checkAcconto(String s){
		{  
			  for(char c: s.toCharArray()){
				  if(!Character.isDigit(c)){
					  return false;
				  }
			  }  
			  return true;
	}
		
}
	
	private double restituisciAcconto(){
		if(acconto.getText().equals(""))
			return 0.0;
		return Double.valueOf(acconto.getText());
	}
}