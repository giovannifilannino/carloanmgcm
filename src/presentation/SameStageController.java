package presentation;

import java.io.IOException;

import business.entity.Contratto;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public abstract class SameStageController implements StageIF{
	
	private static Stage stage = new Stage();
	private static Contratto contratto = new Contratto();
	private final String CARTELLA_CONTROLLER = "controller/fxmlclass/";
	private final String ESTENSIONE_CONTROLLER = ".fxml";
	private String CONTROLLER;
	private String percorso_finale;
	
	public void show(){
		try {
			
			percorso_finale = CARTELLA_CONTROLLER + CONTROLLER + ESTENSIONE_CONTROLLER;
			FXMLLoader fx = new FXMLLoader();
			fx.setLocation(StageController.class.getResource(percorso_finale));
			stage.setTitle(this.getClass().getName());
			stage.setScene(new Scene(fx.load()));
			stage.show();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	
	protected static Contratto getContratto(){
		return contratto;
	}
	
	public void setController(String controller){
		CONTROLLER = controller;
	}



	public Stage getStage() {
		// TODO Auto-generated method stub
		return stage;
	}
}
