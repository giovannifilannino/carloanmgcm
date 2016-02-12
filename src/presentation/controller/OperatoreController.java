package presentation.controller;


import java.io.IOException;
import java.sql.SQLException;

import presentation.FrontController;
import presentation.Main;
import presentation.StageController;
import presentation.controller.utility.ImageGetter;
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

public class OperatoreController extends StageController{



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
	@FXML
	Button refresh;
	
	Image logopic = ImageGetter.getLogo();
	
	static boolean fromOperator = false;
	static String sede_nome;
    static Stage prenotazione1;
	
	static ObservableList<Automobile> auto = FXCollections.observableArrayList();
	static ObservableList<Contratto> contratto = FXCollections.observableArrayList();
	
	Contratto con = new Contratto();
	Automobile autodao = new Automobile();
	
	public void initialize() throws SQLException{
		logo.setImage(logopic);
		auto.addAll(autodao.getAuto(LoginController.getAgenzia()));
		contratto.addAll(con.getNoleggiA(LoginController.getAgenzia()));
		setAutoData(); //inserimento dei dati nelle tabelle
		setContrattoData(); //inserimento dei dati nelle tabelle
		nome.setText(LoginController.getName());
		cognome.setText(LoginController.getCognome());
		sede.setText(LoginController.getAgenzia());
	}
	
	@Override
	public void show() {
		super.setController("FinestraOperatore");
		super.show();
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
	
	
	
	@FXML
	public void nuovo_noleggio(ActionEvent e){
		FrontController.getIstance().dispatchRequest("FinestraPrenotazione");
	}
	
	@FXML
	public void conferma_noleggio(ActionEvent e) throws SQLException{
		int indice = noleggio.getSelectionModel().getSelectedIndex();
		Contratto contratto_damodificare = (Contratto) noleggio.getItems().get(indice);
		if(contratto_damodificare.getStato().equalsIgnoreCase("non confermato")){
			contratto_damodificare.setConferma();
			noleggio.refresh();
			con.confermaNoleggio(contratto_damodificare.getTarga());
		} else
			Popup.Errore("Errore operazione", "Il contratto è già confermato o chiuso");
	}
	
	@FXML
	public void chiudi_noleggio(ActionEvent e) throws SQLException{
		int indice = noleggio.getSelectionModel().getSelectedIndex();
		Contratto contratto = (Contratto) noleggio.getItems().get(indice);
		if(contratto.getStato().equalsIgnoreCase("confermato")){
			contratto.chiudiContratto();
			noleggio.refresh();
			con.chiudiNoleggio(contratto.getTarga());
		} else
			Popup.Errore("Errore operazione", "Il contratto non è ancora confermato o già chiuso");
		
	}
	
	@FXML
	public void inserire_auto(ActionEvent e){
		FrontController.getIstance().dispatchRequest("inserimentoauto");
	}
	

	
	@Override
	public void closeStage() {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void eliminaAuto(ActionEvent e) throws SQLException{
		int indice = automobili_table.getSelectionModel().getSelectedIndex();
		Automobile autodelete = (Automobile) automobili_table.getItems().get(indice);
		if(autodelete.getDisponibile()==0){
			Popup.Errore("Errore nell'eliminazione", "Non puoi eliminare una macchina in uso");
		} else {
			autodao.delete(autodelete.getTarga());
			auto.remove(indice);
		}
		
	}
	
	@FXML
	public void refresh(){
		noleggio.refresh();
		automobili_table.refresh();
	}

	
}
