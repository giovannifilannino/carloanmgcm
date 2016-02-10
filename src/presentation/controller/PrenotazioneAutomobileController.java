package presentation.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import business.entity.Automobile;
import business.entity.Azienda;
import business.entity.Categoria;
import business.entity.CategoriaAutomobile;
import presentation.FrontController;
import presentation.SameStageController;
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

public class PrenotazioneAutomobileController extends SameStageController{

	@Override
	public void show() {
		super.setController("FinestraPrenotazioneAutomobile");
		super.show();
	}

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
	
	ObservableList<CategoriaAutomobile> categorie;
	ObservableList<Automobile> automobili;
	ObservableList<Azienda> agenziee;
	static boolean dati = false;
	String targa;
	Hashtable<String,String> autodizio = new Hashtable<String,String>();
	
	Automobile auto = new Automobile();
	CategoriaAutomobile categoriadao = new CategoriaAutomobile();
	Azienda aziendadao = new Azienda();
	
	
	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() throws SQLException{
	
		automobili = FXCollections.observableArrayList();
		categorie = FXCollections.observableArrayList();
		agenziee = FXCollections.observableArrayList();
		
		
		setAgenzie();
		setCategorie();
		
		categoria.setItems(categorie);
		
		agenzie.setItems(agenziee);
		
		if(!super.getContratto().emptyContratto()){
			categoria.setValue(super.getContratto().getCategoria());
			automobile.setValue(super.getContratto().getAuto());
			agenzie.setValue(super.getContratto().getRestituzione().toString());
		} else {
			automobile.setDisable(true);
			agenzie.setDisable(true);
		}
	}
	
	
	private void setAgenzie() throws SQLException{
		agenziee.addAll(aziendadao.getAll());
	}
	
	private void setCategorie() throws SQLException{
		categorie.addAll(categoriadao.getAll());
	}
	
	
	@FXML
	public void setAutomobile(ActionEvent e) throws SQLException{
		automobili = FXCollections.observableArrayList();
		if(categoria.getValue()!=null){
			automobile.setDisable(false);
			automobili.addAll(auto.getAutoDisponibili(super.getContratto().getPrelievo().toString(), categoria.getValue().toString()));
			fillDizio();
			automobile.setItems(automobili);
		}
	}
	
	private void fillDizio(){
		for(Automobile auto : automobili){
			autodizio.put(auto.getTarga(), auto.getModello_auto());
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
			super.getContratto().setCategoria(categoria.getValue().toString());
			super.getContratto().setAuto(new Automobile(automobile.getValue().toString()));
			super.getContratto().setRestituzione(new Azienda(agenzie.getValue().toString()));
			super.getContratto().setTarga(autodizio.get(automobile.getValue()));
			super.getContratto().setAcconto(restituisciAcconto());
		}
	}
	
	@FXML
	public void indietro(ActionEvent e){
		FrontController.getIstance().dispatchRequest("FinestraPrenotazione");
	}
	
	@FXML
	public void avanti(ActionEvent e){
		String error = "";
		String agenzia = agenzie.getValue().toString();
		String categoriette = categoria.getValue().toString();
		String automobile = categoria.getValue().toString();
		if(agenzia.equals("")){
			error += "Non hai inserito l'agenzia!\n";
		} 
		if (categoriette==""){
			error += "Non hai inserito la categoria\n";
		} 
		if(automobile==""){
			error += "Non hai inserito l'automobile\n";
		} 
		if(error.equalsIgnoreCase("")){
			FrontController.getIstance().dispatchRequest("finestraconfermaprenotazione");
		}
		
		if(!error.equalsIgnoreCase("")){
			Popup.Errore("Errore inserimento", error);
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


	@Override
	public void closeStage() {
		// TODO Auto-generated method stub
		
	}
}