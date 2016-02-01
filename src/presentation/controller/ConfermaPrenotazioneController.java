package presentation.controller;

import integration.CarLoanDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import presentation.FrontController;
import presentation.SameStageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ConfermaPrenotazioneController extends SameStageController{

	@Override
	public void show() {
		super.setController("FinestraConfermaPrenotazione");
		super.show();
	}

	@FXML
	Label tipo_chilometraggio;
	@FXML
	Label tipo_noleggio;
	@FXML
	Label data_inizio;
	@FXML
	Label data_fine;
	@FXML
	Label automobile;
	@FXML
	Label agenzia_restituzione;
	@FXML
	Label costo_previsto;
	
	CarLoanDB db = new CarLoanDB();
	
	
	@FXML
	public void initialize(){
		
		tipo_chilometraggio.setText((super.getContratto().getChilometraggio_limitato()));
		tipo_noleggio.setText((super.getContratto().getNoleggio()));
		data_inizio.setText(super.getContratto().getData_inizio().toString());
		data_fine.setText(super.getContratto().getData_fine().toString());
		automobile.setText(super.getContratto().getAuto().toString());
		agenzia_restituzione.setText(super.getContratto().getRestituzione().toString());
		costo_previsto.setText(String.valueOf(super.getContratto().preventivo()));
		
	}
	
	@FXML
	public void indietro(ActionEvent e){
		FrontController.getIstance().dispatchRequest("FinestraPrenotazioneAutomobile");
	}
	
	@FXML
	public void prenota(ActionEvent e) throws SQLException{
		System.out.println(super.getContratto().getTarga() + " mlmlml");
		Alert conferma = new Alert(AlertType.CONFIRMATION);
		conferma.setTitle("Prenotazione noleggio");
		conferma.setHeaderText("Vuoi confermare i dati inseriti?");
		

		Optional<ButtonType> result = conferma.showAndWait();
		if (result.get() == ButtonType.OK){
			
		    
			if(OperatoreController.fromOperator){
				db.setNoleggio(super.getContratto().getTarga(),null, super.getContratto().getPrelievo().toString(), getKm(super.getContratto().getChilometraggio_limitato()), getNoleggio(super.getContratto().getNoleggio()), super.getContratto().getData_inizio().toString(), super.getContratto().getData_fine().toString(), super.getContratto().getPrelievo().toString(), super.getContratto().getRestituzione().toString(), super.getContratto().getAcconto(), super.getContratto().getAuto());
				OperatoreController.contratto.add(super.getContratto());
				OperatoreController.fromOperator = false;
			} else {
				db.setNoleggio(super.getContratto().getTarga(),LoginController.username, super.getContratto().getPrelievo().toString(), getKm(super.getContratto().getChilometraggio_limitato()), getNoleggio(super.getContratto().getNoleggio()),super.getContratto().getData_inizio().toString(), super.getContratto().getData_fine().toString(), super.getContratto().getPrelievo().toString(), super.getContratto().getRestituzione().toString(), super.getContratto().getAcconto(), super.getContratto().getAuto());
				ClientiController.noleggi_cliente.add(super.getContratto());
				ClientiController.fromClient = false;
			}
			super.getContratto().resetContratto();
			PrenotazioneAutomobileController.dati = false;
			super.getStage().close();
		} else {
		    conferma.close();
		}
	}
	
	private int getKm(String s){
		int output = 0;
		if(s.equalsIgnoreCase("limitato")){
			output = 1;
		} 
		return output;
	}
	
	private int getNoleggio(String s){
		int output = 0;
		if(s.equalsIgnoreCase("giornaliero")){
			output = 1;
		} 
		return output;
	}

}
