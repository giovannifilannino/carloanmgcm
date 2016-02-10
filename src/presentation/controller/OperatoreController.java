package presentation.controller;


import java.io.IOException;
import java.sql.SQLException;
import presentation.FrontController;
import presentation.Main;
import presentation.StageController;
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

	@Override
	public void show() {
		super.setController("FinestraOperatore");
		super.show();
	}

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
	
	static ObservableList<Automobile> auto = FXCollections.observableArrayList();
	static ObservableList<Contratto> contratto = FXCollections.observableArrayList();
	
	Contratto con = new Contratto();
	Automobile autodao = new Automobile();
	
	public void initialize(){
		logo.setImage(logopic);
		setAuto();
		setContratto();
		
		setAutoData();
		setContrattoData();
		nome.setText(LoginController.getName());
		cognome.setText(LoginController.getCognome());
		sede.setText(LoginController.getAgenzia());
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
		Contratto appoggio = contratto_damodificare;
		if(contratto_damodificare.getStato().equalsIgnoreCase("non confermato")){
			appoggio.setConferma();
			contratto.add(appoggio);
			contratto.remove(indice);
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
			this.contratto.add(contratto);
			this.contratto.remove(indice);
			con.chiudiNoleggio(contratto.getTarga());
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
	
	private void setAuto(){
		try {
			auto.addAll(autodao.getAuto(sede_nome));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void setContratto(){
		try {
			contratto.addAll(con.getNoleggiA(sede_nome));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void closeStage() {
		// TODO Auto-generated method stub
		
	}
	
}
