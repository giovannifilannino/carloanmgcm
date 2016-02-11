package presentation.controller;

import java.sql.SQLException;

import presentation.FrontController;
import presentation.Main;
import presentation.StageController;
import presentation.controller.utility.ImageGetter;
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

public class ClientiController extends StageController{


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
	
	Contratto con = new Contratto();
	
	
	Image logopic = ImageGetter.getLogo();
	
	static boolean fromClient = false;
	
	final static ObservableList<Contratto> noleggi_cliente = FXCollections.observableArrayList();
	
	@FXML
	public void initialize() throws SQLException{
		nome.setText(LoginController.getName());
		cognome.setText(LoginController.getCognome());
		noleggi_cliente.addAll(con.getNoleggiC(LoginController.username));
		setContrattiTable();
		logo.setImage(logopic);
	}
	
	@Override
	public void show() {
		super.setController("FinestraClienti");
		super.show();
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
		FrontController.getIstance().dispatchRequest("FinestraPrenotazione");
	}
	
	@Override
	public void closeStage() {
		// TODO Auto-generated method stub
		
	}
	

}
