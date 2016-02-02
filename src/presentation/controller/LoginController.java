package presentation.controller;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import integration.CarLoanDB;
import presentation.FrontController;
import presentation.Main;
import presentation.StageController;
import presentation.controller.utility.Popup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController extends StageController{

	
	
	

	@FXML
	Button enter;
	@FXML
	Button exit_btn;
	@FXML
	TextField user;
	@FXML
	PasswordField pass;
	@FXML
	ImageView logo;
	@FXML
	Hyperlink registero;
	
	private static  String nome;
	private  static String cognome;
	private static String agenzia;
	
	
	static String username;
	
	CarLoanDB db = new CarLoanDB();
	ResultSet credenziali;
	ResultSet credenziali_operatore;
	
	Image logopic = new Image(Main.class.getResourceAsStream("controller/utility/logo.png"));
	
	
	@FXML
	public void initialize(){
		logo.setImage(logopic);
	}
	
	@Override
	public void show() {
		super.setController("LoginWin");
		super.show();
	}
	
	public void closeStage(){
		Stage stage = (Stage) exit_btn.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void login(ActionEvent e) throws SQLException{
		if(db.checkCredenzialiClienti(user.getText(), pass.getText()) ){
			Parent root;
			username = user.getText(); //salva l'informazione dell'username da passare all'interfaccia cliente o operatore
			
			
				
				
				//setta le informazioni nelle finestre username e operatore
				credenziali = db.getDatiUtenti(user.getText());
				
				while(credenziali.next()){
					nome  = credenziali.getString("nomecliente");
					cognome = credenziali.getString("cognomecliente");
				}
				
				
				
				FrontController.getIstance().setAutenticato();
				FrontController.getIstance().dispatchRequest("FinestraClienti");
				
		} else if(db.checkCredenzialiAgenti(user.getText(), pass.getText())){
			FrontController.getIstance().setAutenticato();
			try {
				
				
				setDataAgente();
				
				//Caricamento nuova finestra e chiusura finestra login
				FrontController.getIstance().dispatchRequest("FinestraOperatore");
				Stage stage = (Stage) exit_btn.getScene().getWindow();
				stage.close();
				
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}else if(user.getText().equals("capone") && pass.getText().equalsIgnoreCase("capone")){
			FrontController.getIstance().setAutenticato();
			try {
				FrontController.getIstance().dispatchRequest("FinestraAmministrazione");
				Stage stage = (Stage) exit_btn.getScene().getWindow();
				stage.close();
				
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
			
		else{
			Popup.Errore("Credenziali errate", "Hai inserito credenziali sbagliate.");
		}
			
	}
	
	//dato l'username mi restituisce i dati dell'agente
	private void setDataAgente() throws SQLException{
		credenziali_operatore = db.getDatiAgente(user.getText());
		while(credenziali_operatore.next()){
			nome = credenziali_operatore.getString("nomeagente");
			cognome = credenziali_operatore.getString("cognomeagente");
			agenzia = credenziali_operatore.getString("codagenzia");
		}
	}
	
	@FXML
	public void exit(ActionEvent e){
		Stage stage = (Stage) exit_btn.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void register(ActionEvent e){
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("fxmlclass/RegisterWindows.fxml"));
			Stage a = new Stage();
			a.setTitle("Registrazione");
			a.setScene(new Scene(root));
			a.show();
			//((Node) e.getSource()).getScene().getWindow().hide();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public static String getName(){
		return nome;
	}
	
	public static String getCognome(){
		return cognome;
	}
	
	public static String getAgenzia(){
		return agenzia;
	}
	
	private void setName(String name){
		this.nome = nome;
	}
	
	

	
	
}
