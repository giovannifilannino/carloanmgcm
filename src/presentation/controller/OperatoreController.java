package presentation.controller;

import integration.CarLoanDB;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import presentation.Main;
import presentation.controller.utility.Popup;
import business.entity.Automobile;
import business.entity.Contratto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class OperatoreController {

	@FXML
	Label nome;
	@FXML
	Label cognome;
	@FXML
	Label sede;
	@FXML
	TableView automobili_table;
	@FXML
	TableView noleggio;
	@FXML
	TableColumn targa;
	@FXML
	TableColumn modello;
	@FXML
	TableColumn cilindrata;
	@FXML
	TableColumn categoria;
	@FXML
	TableColumn disponibile;
	@FXML
	TableColumn cliente;
	@FXML
	TableColumn automobile;
	@FXML
	TableColumn tipo_noleggio;
	@FXML
	TableColumn chilometraggio;
	@FXML
	TableColumn stato_noleggio;
	@FXML
	Button inserisci_auto;
	@FXML
	Button elimina_auto;
	@FXML
	Button chiudi_noleggio_btn;
	@FXML
	Button nuovo_noleggio_btn;
	@FXML
	Button conferma;
	@FXML
	ImageView logo;
	
	Image logopic = new Image(Main.class.getResourceAsStream("controller/utility/logo.png"));
	
	static boolean fromOperator = false;
	static String sede_nome;
    static Stage prenotazione1;
	
	CarLoanDB db = new CarLoanDB();
	
	ResultSet autoset;
	ResultSet contrattoset;
	
	static ObservableList<Automobile> auto = FXCollections.observableArrayList();
	static ObservableList<Contratto> contratto = FXCollections.observableArrayList();
	
	public void initialize(){
		logo.setImage(logopic);
		
		try {
			setAuto();
			setContratto();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		setAutoData();
		setContrattoData();
	}
	
	private void setAutoData(){
		automobili_table.setItems(auto);
		targa.setCellValueFactory(new PropertyValueFactory<Automobile,String>("targa"));
		modello.setCellValueFactory(new PropertyValueFactory<Automobile,String>("modello_auto"));
		cilindrata.setCellValueFactory(new PropertyValueFactory<Automobile,String>("cilindrata"));
		categoria.setCellValueFactory(new PropertyValueFactory<Automobile,String>("categoria"));
		disponibile.setCellValueFactory(new PropertyValueFactory<Automobile,String>("disponibile"));
	}
	
	private void setContrattoData(){
		noleggio.setItems(contratto);
		cliente.setCellValueFactory(new PropertyValueFactory<Contratto,String>("cliente"));
		automobile.setCellValueFactory(new PropertyValueFactory<Contratto,String>("auto"));
		tipo_noleggio.setCellValueFactory(new PropertyValueFactory<Contratto,String>("noleggio"));
		chilometraggio.setCellValueFactory(new PropertyValueFactory<Contratto,String>("chilometraggio_limitato"));
		stato_noleggio.setCellValueFactory(new PropertyValueFactory<Contratto,String>("stato"));
	}
	
	public void setData(String string, String string2, String string3) {
		
		nome.setText(string);
		cognome.setText(string2);
		sede.setText(string3);
		
	}
	
	public void setSede(String agenzia){
		sede_nome = agenzia;
	}
	
	@FXML
	public void nuovo_noleggio(ActionEvent e){
		try {
			fromOperator = true;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("fxmlclass/FinestraPrenotazione.fxml"));
			Stage a = new Stage();
			a.setTitle("Noleggio Auto");
			a.setScene(new Scene(loader.load()));
			ClientiController.prenotazione1 = a;
			a.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@FXML
	public void conferma_noleggio(ActionEvent e) throws SQLException{
		int indice = noleggio.getSelectionModel().getSelectedIndex();
		Contratto contratto_damodificare = (Contratto) noleggio.getItems().get(indice);
		Contratto appoggio = contratto_damodificare;
		if(contratto_damodificare.getStato().equalsIgnoreCase("non confermato")){
			appoggio.setConferma();
			contratto.add(appoggio);
			contratto.remove(indice);
			db.confermaNoleggio(contratto_damodificare.getTarga());
		} else
			Popup.Errore("Errore operazione", "Il contratto è già confermato o chiuso");
	}
	
	@FXML
	public void chiudi_noleggio(ActionEvent e) throws SQLException{
		int indice = noleggio.getSelectionModel().getSelectedIndex();
		Contratto contratto = (Contratto) noleggio.getItems().get(indice);
		if(contratto.getStato().equalsIgnoreCase("confermato")){
			contratto.chiudiContratto();
			this.contratto.add(contratto);
			this.contratto.remove(indice);
			
			db.chiudiNoleggio(contratto.getTarga());
		} else
			Popup.Errore("Errore operazione", "Il contratto non è ancora confermato o già chiuso");
		
	}
	
	@FXML
	public void inserire_auto(ActionEvent e){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("fxmlclass/InserimentoAuto.fxml"));
			Stage a = new Stage();
			a.setTitle("Noleggio Auto");
			a.setScene(new Scene(loader.load()));
			a.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void setAuto() throws SQLException{
		
		autoset = db.getAuto(sede_nome);
		while(autoset.next()){
			String targa = autoset.getString("targa");
			String nomeauto = autoset.getString("nomeauto");
			int cilindrata = autoset.getInt("cilindrata");
			String agenzia = autoset.getString("codagenzia");
			String categoria = autoset.getString("codfascia");
			auto.add(new Automobile(nomeauto, targa, cilindrata, agenzia, categoria));
		}
		
	}
	
	private void setContratto() throws SQLException{
		contrattoset = db.getNoleggiA(sede_nome);
		while(contrattoset.next()){
			
			String targa = contrattoset.getString("targa");
			String username = contrattoset.getString("usernamec");
			Boolean tipokm = getValue(contrattoset.getInt("tipoKilometraggio"));
			Boolean tiponoleggio = getValue(contrattoset.getInt("tiponoleggio"));
			LocalDate inizio = LocalDate.parse(contrattoset.getString("inizionoleggio"));
			LocalDate fine = LocalDate.parse(contrattoset.getString("finenoleggio"));
			String ritiro = contrattoset.getString("cittaritiro");
			String consegna = contrattoset.getString("cittaconsegna");
			Double acconto = contrattoset.getDouble("acconto");
			String nomeauto = contrattoset.getString("nomeauto");
			String stato = contrattoset.getString("chiuso");
			Contratto contratto_daaggiungere = new Contratto(targa, username, nomeauto, tipokm, tiponoleggio, ritiro, consegna, acconto);
			contratto_daaggiungere.setStato(stato);
			contratto.add(contratto_daaggiungere);
			
		}
	}
	
	private boolean getValue(int i){
		if(i==0){
			return true;
		}
		return false;
	}
	
}
