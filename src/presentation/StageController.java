package presentation;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class StageController implements StageIF{

	private final String CARTELLA_CONTROLLER = "controller/fxmlclass/";
	private final String ESTENSIONE_CONTROLLER = ".fxml";
	private String CONTROLLER;
	private String percorso_finale;
	
	public void show(){
		try {
			
			percorso_finale = CARTELLA_CONTROLLER + CONTROLLER + ESTENSIONE_CONTROLLER;
			FXMLLoader fx = new FXMLLoader();
			fx.setLocation(StageController.class.getResource(percorso_finale));
			Stage a = new Stage();
			a.setTitle(this.getClass().getName());
			a.setScene(new Scene(fx.load()));
			a.show();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void setController(String controller){
		CONTROLLER = controller;
	}
}
