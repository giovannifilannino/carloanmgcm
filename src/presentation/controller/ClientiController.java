package presentation.controller;


import integration.CarLoanDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import presentation.FrontController;
import presentation.Main;
import presentation.StageController;
import business.entity.Automobile;
import business.entity.Cliente;
import business.entity.Contratto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ClientiController extends StageController{

	@Override
	public void show() {
		super.setController("FinestraClienti");
		super.show();
	}

	@FXML
	Label nome;
	@FXML
	Label cognome;
	@FXML
	Button prenota;
	@FXML
	Button delete;
	@FXML
	TableView storico_noleggi;
	@FXML
	TableColumn modello_auto;
	@FXML
	TableColumn targa;
	@FXML
	TableColumn km;
	@FXML
	TableColumn stato;
	@FXML
	ImageView logo;
	
	FrontController control = FrontController.getIstance();
	
	Image logopic = new Image(Main.class.getResourceAsStream("controller/utility/logo.png"));
	
	static boolean fromClient = false;
	
	CarLoanDB db = new CarLoanDB();
	ResultSet contratti;
	
	final static ObservableList<Contratto> noleggi_cliente = FXCollections.observableArrayList();
	
	@FXML
	public void initialize() throws SQLException{
		
		nome.setText(LoginController.getName());
		cognome.setText(LoginController.getCognome());
		setContratti();
		setContrattiTable();
		logo.setImage(logopic);
	}
	
	private void setContratti() throws SQLException{
		contratti = db.getNoleggiC(LoginController.username);
		while(contratti.next()){
			String targa = contratti.getString("targa");
			String username = contratti.getString("usernamec");
			Boolean tipokm = getValue(contratti.getInt("tipoKilometraggio"));
			String nomeauto = contratti.getString("nomeauto");
			String stato = contratti.getString("chiuso");
			Contratto con = new Contratto();
			con.setTarga(targa);
			con.setCliente(new Cliente(username));
			con.setChilometraggio_limitato(tipokm);
			con.setAuto(new Automobile(nomeauto));
			con.setStato(stato);
			noleggi_cliente.add(con);
		}
	}
	
	private void setContrattiTable(){
		storico_noleggi.setItems(noleggi_cliente);
		modello_auto.setCellValueFactory(new PropertyValueFactory<Contratto,String>("auto"));
		targa.setCellValueFactory(new PropertyValueFactory<Contratto,String>("targa"));
		km.setCellValueFactory(new PropertyValueFactory<Contratto,String>("chilometraggio_limitato"));
		stato.setCellValueFactory(new PropertyValueFactory<Contratto,String>("stato"));
		
	}
	
	public void setData(String nome_cliente, String cognome_cliente){
		this.nome.setText(nome_cliente);
		this.cognome.setText(cognome_cliente);
	}
	
	@FXML
	public void apriNoleggio(ActionEvent e){
		control.dispatchRequest("FinestraPrenotazione");
	}
	
	private boolean getValue(int i){
		if(i==0){
			return true;
		}
		return false;
	}

	@Override
	public void closeStage() {
		// TODO Auto-generated method stub
		
	}
	

}
