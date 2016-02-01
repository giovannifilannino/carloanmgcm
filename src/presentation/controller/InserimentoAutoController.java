package presentation.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import business.entity.Automobile;
import presentation.StageController;
import presentation.controller.utility.Popup;
import integration.CarLoanDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InserimentoAutoController extends StageController{

	@Override
	public void show() {
		super.setController("InserimentoAuto");
		super.show();
	}

	@FXML
	TextField targa;
	@FXML
	TextField modello_auto;
	@FXML
	TextField cilindrata;
	@FXML
	ComboBox<String> agenzia;
	@FXML
	ComboBox<String> categorie;
	@FXML
	Button esci_btn;
	
	
	CarLoanDB db = new CarLoanDB();
	ResultSet agenzieset;
	ResultSet categorieset;
	ObservableList<String> agenzielist = FXCollections.observableArrayList();
	ObservableList<String> categorielist = FXCollections.observableArrayList();
	
	@FXML
	public void initialize() throws SQLException{
		agenzieset = db.getAgenzie();
		categorieset = db.getCategorie();
		setAgenzie();
		setCategorie();
		agenzia.setItems(agenzielist);
		categorie.setItems(categorielist);
	}
	
	
	@FXML
	public void conferma(ActionEvent e) throws SQLException{
		if(targa.getText()!=null && modello_auto.getText() != null && agenzia.getValue() != null && categorie.getValue() != null && isNumeric(cilindrata.getText())){
			db.setAuto(targa.getText(), modello_auto.getText(), getCilindrata(cilindrata.getText()), agenzia.getValue().toString(), categorie.getValue().toString());
			OperatoreController.auto.add(new Automobile(targa.getText(), modello_auto.getText(), getCilindrata(cilindrata.getText()), agenzia.getValue(), categorie.getValue()));
			esci();
		} else {
			Popup.Errore("Errore inserimento", "Non hai inserito tutti i dati");
		}
	}
	
	private void setAgenzie() throws SQLException{
		while(agenzieset.next()){
			agenzielist.add(agenzieset.getString("nomeagenzia"));
		}
	}
	
	private void setCategorie() throws SQLException{
		while(categorieset.next()){
			categorielist.add(categorieset.getString("nomefascia"));
		}
	}
	
	@FXML
	public void esci(ActionEvent e){
		esci();
	}
	
	private static boolean isNumeric(String str)  
	{  
	  for(char c: str.toCharArray()){
		  if(!Character.isDigit(c)){
			  return false;
		  }
	  }  
	  return true;
	}
	
	private int getCilindrata(String s){
		int value = Integer.valueOf(s);
		if(value<=900){
			value = 900;
		}
		else if(value>3000){
			value = 3000;
		}
		return value;
	}
	
	private void esci(){
		Stage s = (Stage) esci_btn.getScene().getWindow();
		s.close();
	}
} 
