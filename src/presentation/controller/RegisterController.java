package presentation.controller;


import java.sql.SQLException;
import java.util.Optional;
import business.entity.Cliente;
import presentation.Main;
import presentation.StageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RegisterController extends StageController{
	
	@Override
	public void show() {
		super.setController("RegisterWindows");
		super.show();
	}

	ToggleGroup a = new ToggleGroup();

	@FXML
	TextField nome;
	@FXML
	TextField cognome;
	@FXML
	TextField username;
	@FXML
	TextField password;
	@FXML
	TextField nrtelefono;
	@FXML
	MenuButton opzioni;
	@FXML
	ImageView logo;
	@FXML
	Button conferma_btn;
	@FXML
	Button esci_btn;
	
	@FXML
	RadioButton patentato;
	@FXML
	RadioButton neo_patentato;
	
	Cliente cl = new Cliente();
	
	Image logoimage = new Image(Main.class.getResourceAsStream("controller/utility/logo.png"));
	
	@FXML
	public void initialize(){
		patentato.setToggleGroup(a);
		neo_patentato.setToggleGroup(a);
		logo.setImage(logoimage);
		patentato.setSelected(true);
	}
	
	@FXML
	public void esci(ActionEvent e){
		closeStage();
		
	}
	
	@FXML
	public void conferma(ActionEvent e) throws SQLException{
		
		if(nome.getText().length()<30 && cognome.getText().length()<30 && isNumeric(nrtelefono.getText()) && nrtelefono.getText().length()==10 && !cl.checkEsistenzaCliente(username.getText())){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Sei sicuro?");
			alert.setContentText("Vuoi confermare i dati inseriti?");

			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				String banana = nrtelefono.getText();
				Cliente daaggiungere = new Cliente(username.getText(), password.getText(), nome.getText(), cognome.getText(), getPatentato(), nrtelefono.getText());
				cl.create(daaggiungere);
			   alert.close();
			   closeStage();
			} else {
			   alert.close();
			}
			
		} else {
			String errorMessage = "";
			if(nome.getText().length()>=30 || nome.getText().length()<3){
				errorMessage = errorMessage + "Non hai inserito un nome valido." + "\n";
			}
			if(cognome.getText().length()>30){
				errorMessage = errorMessage + "Non hai inserito un cognome valido." + "\n";
			}
			if(nrtelefono.getText().length()!=10){
				errorMessage = errorMessage + "Non hai inserito un numero di telefono valido." + "\n";
			}
			if(!isNumeric(nrtelefono.getText())){
				errorMessage = errorMessage + "Non hai inserito numeri, ma caratteri." + "\n";
			}
			if(cl.checkEsistenzaCliente(username.getText())){
				errorMessage = errorMessage + "Username già presente" + "\n";
			}
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore inserimento");
			alert.setContentText(errorMessage);
			Optional<ButtonType> result = alert.showAndWait();
		}
	}
	
	private int getPatentato(){
		if(neo_patentato.isSelected())
			return 1;
		return 0;
	}
	
	private static boolean isNumeric(String str)  
	{  
	  for(char c: str.toCharArray()){
		  if(!Character.isDigit(c))
			  return false;
	  }  
	  return true;
	}

	@Override
	public void closeStage() {
		Stage a = (Stage) esci_btn.getScene().getWindow();
		a.close();
		
	}
	
}
