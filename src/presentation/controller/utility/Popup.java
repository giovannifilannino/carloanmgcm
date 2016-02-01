package presentation.controller.utility;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Popup {

	public static void Errore(String titolo, String contenuto){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titolo);
		alert.setContentText(contenuto);
		Optional<ButtonType> result = alert.showAndWait();
	}
	
}
