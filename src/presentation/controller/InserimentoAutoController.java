package presentation.controller;


import java.sql.SQLException;

import business.entity.Automobile;
import business.entity.Azienda;
import business.entity.CategoriaAutomobile;
import presentation.StageController;
import presentation.controller.utility.Popup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InserimentoAutoController extends StageController{


	@FXML
	TextField targa;
	@FXML
	TextField modello_auto;
	@FXML
	TextField cilindrata;
	@FXML
	ComboBox<Azienda> agenzia;
	@FXML
	ComboBox<CategoriaAutomobile> categorie;
	@FXML
	Button esci_btn;
	
	Azienda az = new Azienda();
	CategoriaAutomobile ca = new CategoriaAutomobile();
	Automobile auto = new Automobile();
	
	ObservableList<Azienda> agenzielist = FXCollections.observableArrayList();
	ObservableList<CategoriaAutomobile> categorielist = FXCollections.observableArrayList();
	
	@FXML
	public void initialize() throws SQLException{
		agenzielist.addAll(az.getAll());
		categorielist.addAll(ca.getAll());
		agenzia.setItems(agenzielist);
		categorie.setItems(categorielist);
	}
	
	@Override
	public void show() {
		super.setController("InserimentoAuto");
		super.show();
	}

	
	@FXML
	public void conferma(ActionEvent e) throws SQLException{
		if(targa.getText()!=null && modello_auto.getText() != null && agenzia.getValue() != null && categorie.getValue() != null && isNumeric(cilindrata.getText())){
			auto.create(new Automobile(modello_auto.getText(), targa.getText(), getCilindrata(cilindrata.getText()),agenzia.getValue().toString(), categorie.getValue().toString()));
			OperatoreController.auto.add(new Automobile( modello_auto.getText(),targa.getText(), getCilindrata(cilindrata.getText()), agenzia.getValue().toString(), categorie.getValue().toString()));
			closeStage();
		} else {
			Popup.Errore("Errore inserimento", "Non hai inserito tutti i dati");
		}
	}

	
	@FXML
	public void esci(ActionEvent e){
		closeStage();
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

	@Override
	public void closeStage() {
		Stage s = (Stage) esci_btn.getScene().getWindow();
		s.close();
	}
} 
