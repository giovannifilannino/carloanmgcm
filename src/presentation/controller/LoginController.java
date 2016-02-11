package presentation.controller;



import java.sql.SQLException;
import business.entity.Agente;
import business.entity.Cliente;
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
	
	Cliente cl = new Cliente();
	Agente ag = new Agente();
	
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
		if(cl.checkCredenzialiClienti(user.getText(), pass.getText()) ){
			username = user.getText(); //salva l'informazione dell'username da passare all'interfaccia cliente o operatore	
			//setta le informazioni nelle finestre username e operatore
			nome  = cl.read(user.getText()).getNome();
			cognome = cl.read(user.getText()).getCognomeCliente();
			FrontController.getIstance().setAutenticato();
			FrontController.getIstance().dispatchRequest("FinestraClienti");	
		} else if(ag.checkcredenziali(user.getText(), pass.getText())){
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
			nome = ag.read(user.getText()).getNome();
			cognome = ag.read(user.getText()).getCognome();
			agenzia = ag.read(user.getText()).getAgenzia();
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
}
