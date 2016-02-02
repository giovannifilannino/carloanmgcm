package presentation.controller;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import presentation.FrontController;
import presentation.Main;
import presentation.StageController;
import business.entity.Agente;
import business.entity.Azienda;
import integration.CarLoanDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AmministratoreController extends StageController{

	@Override
	public void show() {
		super.setController("FinestraAmministrazione");
		super.show();
	}

	@FXML
	Button inserisci_agente_btn;
	@FXML
	Button elimina_agente_btn;
	@FXML
	Button inserisci_agenzia;
	@FXML
	Button elimina_agenzia;
	@FXML
	TableColumn agenti_nome;
	@FXML
	TableColumn agenti_cognome;
	@FXML
	TableColumn agenti_agenzia;
	@FXML
	TableColumn agenzie;
	@FXML
	TableView agenzie_table;
	@FXML
	TableView agenti_table;
	@FXML
	ImageView logo;
	
	Image logopic = new Image(Main.class.getResourceAsStream("controller/utility/logo.png"));
	CarLoanDB db = new CarLoanDB();
	
	//lista agenzie
	static ObservableList<Azienda> aziende = FXCollections.observableArrayList();
	static ObservableList<Agente> agente = FXCollections.observableArrayList();
	
	ResultSet agenzieset;
	ResultSet agentiset;
	
	
	public void initialize() throws SQLException{
		logo.setImage(logopic);
		
		agenzieset = db.getAgenzie();
		agentiset = db.getAgenti();
		setAgenzie();
		setAgenti();
		fillTable();
	}
	
	private void fillTable(){
		agenzie_table.setItems(aziende);
		agenti_table.setItems(agente);
		agenzie.setCellValueFactory(new PropertyValueFactory<Azienda,String>("nome_azienda"));
		agenti_nome.setCellValueFactory(new PropertyValueFactory<Agente,String>("nome"));
		agenti_cognome.setCellValueFactory(new PropertyValueFactory<Agente,String>("cognome"));
		agenti_agenzia.setCellValueFactory(new PropertyValueFactory<Agente,String>("agenzia"));
	}
	
	private void setAgenzie() throws SQLException{
		while(agenzieset.next()){
			aziende.add(new Azienda(agenzieset.getString("nomeagenzia")));
		}
	}
	
	
	private void setAgenti() throws SQLException{
		while(agentiset.next()){
			String nome = agentiset.getString("nomeagente");
			String cognome = agentiset.getString("cognomeagente");
			String codagenzia = agentiset.getString("codagenzia");
			String usern = agentiset.getString("usernamea");
			agente.add(new Agente(codagenzia,nome,cognome,usern));
		}
	}
	
	@FXML
	public void elimina_agente(ActionEvent e) throws SQLException{
	
		int indice = agenti_table.getSelectionModel().getSelectedIndex();
		Agente agente = (Agente) agenti_table.getItems().get(indice);
		agenti_table.getItems().remove(indice);
		
		String user = agente.getUser();
		db.removeAgente(user);
		
	}
	
	@FXML
	public void inserisci_agente(ActionEvent e){
		FrontController.getIstance().dispatchRequest("InserimentoAgente");
	}
	
	@FXML
	public void inserisci_azienda(ActionEvent e) throws SQLException{
		TextInputDialog dialog = new TextInputDialog("Nome azienda...");
		dialog.setTitle("Inserisci nome azienda");
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()){
			Azienda nuova = new Azienda(result.get());
			aziende.add(nuova);
			db.setAgenzia(nuova.getNome_azienda(), "Puglia");
		}
	}
	
	@FXML
	public void elimina_azienda(ActionEvent e ) throws SQLException{
		int indice = agenzie_table.getSelectionModel().getSelectedIndex();
		Azienda agenzia = (Azienda) agenzie_table.getItems().get(indice);
		agenzie_table.getItems().remove(indice);
		
		String citta = agenzia.getNome_azienda();
		db.removeAgenzia(citta);
		
		for(Agente a : agente){
			if(a.getAgenzia().equals(citta)){
				agente.remove(a);
			}
			//questo break serve poichè la modifica durante un iterazione lancia un eccezione
			if(!agente.iterator().hasNext()){
				break;
			}
		}
	}

	@Override
	public void closeStage() {
		// TODO Auto-generated method stub
		
	}
	
}
