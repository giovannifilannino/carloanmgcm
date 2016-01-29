package presentation.controller;



import java.sql.ResultSet;
import java.sql.SQLException;

import integration.CarLoanDB;
import presentation.FrontController;
import presentation.Main;
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

public class LoginController {

	
	
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
	
	String nome;
	String cognome;
	String agenzia;
	
	static String username;
	
	CarLoanDB db = new CarLoanDB();
	ResultSet credenziali;
	ResultSet credenziali_operatore;
	
	Image logopic = new Image(Main.class.getResourceAsStream("controller/utility/logo.png"));
	
	
	@FXML
	public void initialize(){
		logo.setImage(logopic);
	}
	
	@FXML
	public void login(ActionEvent e) throws SQLException{
		if(db.checkCredenzialiClienti(user.getText(), pass.getText()) ){
			Parent root;
			username = user.getText(); //salva l'informazione dell'username da passare all'interfaccia cliente o operatore
			
			try {
				
				//Caricamento nuova finestra e chiusura finestra login
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("fxmlclass/FinestraClienti.fxml"));
				Stage a = new Stage();
				a.setTitle("Clienti");
				a.setScene(new Scene(loader.load()));
				Stage stage = (Stage) exit_btn.getScene().getWindow();
				stage.close();
				a.show();
				
				//setta le informazioni nelle finestre username e operatore
				ClientiController c = loader.<ClientiController>getController();
				credenziali = db.getDatiUtenti(user.getText());
				while(credenziali.next()){
					nome = credenziali.getString("nomecliente");
					cognome = credenziali.getString("cognomecliente");
				}
				c.setData(nome, cognome);
				
			} catch(Exception e1) {
				e1.printStackTrace();
			}
			
			
		} else if(db.checkCredenzialiAgenti(user.getText(), pass.getText())){
			Parent root;
			try {
				
				
				setDataAgente();
				
				OperatoreController.sede_nome = agenzia;
				
				//Caricamento nuova finestra e chiusura finestra login
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("fxmlclass/FinestraOperatore.fxml"));
				Stage a = new Stage();
				a.setTitle("Operatore");
				a.setScene(new Scene(loader.load()));
				Stage stage = (Stage) exit_btn.getScene().getWindow();
				stage.close();
				OperatoreController c = loader.<OperatoreController>getController();
				
				
				
				c.setData(nome, cognome, agenzia);
				
				
				a.show();
				
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}else if(user.getText().equals("capone") && pass.getText().equalsIgnoreCase("capone")){
			Parent root;
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("fxmlclass/FinestraAmministrazione.fxml"));
				Stage a = new Stage();
				a.setTitle("Amministrazione");
				a.setScene(new Scene(loader.load()));
				Stage stage = (Stage) exit_btn.getScene().getWindow();
				stage.close();
				a.show();
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
	
	public String getName(){
		return nome;
	}
	
	public String getCognome(){
		return cognome;
	}

	
	
}
